package pojo;

import java.util.Map;

/**
 * 保存查询到的结果的实体类
 *
 * @author Administrator
 */
public class DBQueryResult {
    private String no;
    private Map<String, Object> columenLableAndValue;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Map<String, Object> getColumenLableAndValue() {
        return columenLableAndValue;
    }

    public void setColumenLableAndValue(Map<String, Object> columenLableAndValue) {
        this.columenLableAndValue = columenLableAndValue;
    }

}
