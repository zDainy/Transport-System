package common;

import dao.ServiceDao;

public class Service {
    private int id;
    private String name;
    private int price;
    private String descr;
    private int categoryId;
    private String categoryName;

    public Service(int id, String name, int price, String descr, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.descr = descr;
        this.categoryId = categoryId;
        searchCategoryName();
    }

    private void searchCategoryName() {
        this.categoryName = ServiceDao.getCategoryName(this.categoryId);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
