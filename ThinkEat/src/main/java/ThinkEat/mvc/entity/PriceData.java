package ThinkEat.mvc.entity;

public class PriceData {
    protected Integer id;
    protected String name;

    public PriceData() {
    }

    public PriceData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PriceData [id=" + id + ", name=" + name + "]";
    }


}
