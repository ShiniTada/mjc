package com.epam.esm.repository.specification;

public final class ParameterName {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String SORT_BY_NAME = " c.name ";
    public static final String SORT_BY_DATE = " c.createDate ";
    public static final String GROUP_BY_ID = " group by c.id having count(t)= :tagSize ";
    public static final String BY_TAG = " JOIN c.tags t  WHERE t.name in :tags ";
    public static final String BY_NAME = " c.name LIKE CONCAT('%', :partName, '%') ";
    public static final String BY_DESCRIPTION = " c.description LIKE CONCAT('%', :partDescription, '%') ";

    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String ORDER_BY = " order by ";
    public static final String DELIMITER = " , ";
    public static final String TAGS = "tags";
    public static final String PART_NAME = "partName";
    public static final String PART_DESCRIPTION = "partDescription";
    public static final String TAG_SIZE = "tagSize";

    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER = "user";

    private ParameterName() {
    }
}
