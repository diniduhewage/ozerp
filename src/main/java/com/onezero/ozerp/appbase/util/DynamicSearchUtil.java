package com.onezero.ozerp.appbase.util;

import com.onezero.ozerp.appbase.error.exception.DynamicSearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class DynamicSearchUtil {

    @Autowired
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public <T> Page<T> filterEntityQuery(Class<T> entityClass, Map<String, String> reqParam, int page, int size, String sort)
            throws DynamicSearchException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException, InstantiationException, IllegalAccessException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<T> root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.conjunction();
        predicate = predicateBuilder(entityClass, reqParam, criteriaBuilder, predicate, root);
        //order according to the param which send through the query string
        if (sort != null && sort.equalsIgnoreCase("desc")) {
            criteriaQuery.multiselect(root)
                    .where(predicate)
                    .orderBy(criteriaBuilder
                            .desc(root.get("id")));
        } else {
            criteriaQuery.multiselect(root)
                    .where(predicate)
                    .orderBy(criteriaBuilder
                            .asc(root.get("id")));
        }

        TypedQuery<Tuple> createQuery = entityManager.createQuery(criteriaQuery);
        createQuery.setFirstResult((page) * size);
        createQuery.setMaxResults(size);
        List<Tuple> resultList = createQuery.getResultList();

        List<T> entityList = new ArrayList<>();

        resultList.forEach(tuple -> {
            T entity = (T) tuple.get(0);
            entityList.add(entity);
        });

        return new PageImpl<>(entityList, PageRequest.of(page, size), totalCount(entityClass, reqParam));

    }

    public <T> long totalCount(Class<T> entityClass, Map<String, String> reqParam)
            throws DynamicSearchException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.conjunction();

        predicate = predicateBuilder(entityClass, reqParam, criteriaBuilder, predicate, root);
        countQuery.select(criteriaBuilder.count(root)).where(predicate);

        TypedQuery<Long> createQuery = entityManager.createQuery(countQuery);
        return createQuery.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    private <T> Predicate predicateBuilder(Class<T> entityClass, Map<String, String> reqParam,
                                           CriteriaBuilder criteriaBuilder, Predicate predicate, Root<T> root)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        for (Map.Entry<String, String> param : reqParam.entrySet()) {
            T entity = null;
            entity = entityClass.getDeclaredConstructor().newInstance();// entityClass.newInstance();

            Field[] declaredFields = entity.getClass().getDeclaredFields();
            ArrayList<String> fieldList = new ArrayList<>();
            for (Field field : declaredFields) {
                fieldList.add(field.getName());
            }
            if (!fieldList.contains(param.getKey())) {
                throw new DynamicSearchException(param.getKey()
                        + " not found in " + entityClass.getSimpleName() + " table");
            }

            Class<?> fieldType = root.get(param.getKey()).getJavaType();

            if (param.getValue().contains("%")) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get(param.getKey()), (param.getValue())));
            } else if (param.getValue().contains(">=")) {
                String[] values = param.getValue().split(">=");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                        root.get(param.getKey()), (Comparable) convertParamValue(values[1], fieldType)));
            } else if (param.getValue().contains(">")) {
                String[] values = param.getValue().split(">");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get(param.getKey()),
                        (Comparable) convertParamValue(values[1], fieldType)));
            } else if (param.getValue().contains("<=")) {
                String[] values = param.getValue().split("<=");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get(param.getKey()),
                        (Comparable) convertParamValue(values[1], fieldType)));
            } else if (param.getValue().contains("<")) {
                String[] values = param.getValue().split("<");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get(param.getKey()),
                        (Comparable) convertParamValue(values[1], fieldType)));
            } else if (param.getValue().contains("BETWEEN")) {
                String stringWithAnd = param.getValue().replace("BETWEEN", "");
                String[] values = stringWithAnd.split("AND");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.between(root.get(param.getKey()),
                                (Comparable) convertParamValue(values[0].trim(), fieldType),
                                (Comparable) convertParamValue(values[1].trim(), fieldType)));
            } else {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get(param.getKey()), param.getValue()));
            }

        }
        return predicate;
    }

    private Object convertParamValue(String value, Class<?> fieldType) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(value);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(value);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == Float.class || fieldType == float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == BigDecimal.class) {
            return new BigDecimal(value);
        } else if (fieldType == Date.class) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
