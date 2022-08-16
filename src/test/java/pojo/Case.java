package pojo;

/**
 * 保存用例信息
 *
 * @author Administrator
 */
public class Case {
    private String apiId;
    private String caseId;
    private String caseDetil;
    private String params;
    private String expecteResponseData;
    private String actualResponseData;
    private String preValidateSql;
    private String afterValidateSql;
    private String preValidateResult;
    private String afterValidateResult;
    private String isNeedToken;
    private String GlobalVariables;
    private String fileUp;

    public Case() {
        // TODO Auto-generated constructor stub
    }

    public String getFileUp() {
        return fileUp;
    }

    public void setFileUp(String fileUp) {
        this.fileUp = fileUp;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseDetil() {
        return caseDetil;
    }

    public void setCaseDetil(String caseDetil) {
        this.caseDetil = caseDetil;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExpecteResponseData() {
        return expecteResponseData;
    }

    public void setExpecteResponseData(String expecteResponseData) {
        this.expecteResponseData = expecteResponseData;
    }

    public String getActualResponseData() {
        return actualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        this.actualResponseData = actualResponseData;
    }

    public String getPreValidateSql() {
        return preValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        this.preValidateSql = preValidateSql;
    }

    public String getAfterValidateSql() {
        return afterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        this.afterValidateSql = afterValidateSql;
    }

    public String getPreValidateResult() {
        return preValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        this.preValidateResult = preValidateResult;
    }

    public String getAfterValidateResult() {
        return afterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        this.afterValidateResult = afterValidateResult;
    }

    public String getIsNeedToken() {
        return isNeedToken;
    }

    public void setIsNeedToken(String isNeedToken) {
        this.isNeedToken = isNeedToken;
    }

    public String getGlobalVariables() {
        return GlobalVariables;
    }

    public void setGlobalVariables(String globalVariables) {
        GlobalVariables = globalVariables;
    }

    @Override
    public String toString() {
        return "Case{" +
                "apiId='" + apiId + '\'' +
                ", caseId='" + caseId + '\'' +
                ", caseDetil='" + caseDetil + '\'' +
                ", params='" + params + '\'' +
                ", expecteResponseData='" + expecteResponseData + '\'' +
                ", actualResponseData='" + actualResponseData + '\'' +
                ", preValidateSql='" + preValidateSql + '\'' +
                ", afterValidateSql='" + afterValidateSql + '\'' +
                ", preValidateResult='" + preValidateResult + '\'' +
                ", afterValidateResult='" + afterValidateResult + '\'' +
                ", isNeedToken='" + isNeedToken + '\'' +
                ", GlobalVariables='" + GlobalVariables + '\'' +
                ", fileUp='" + fileUp + '\'' +
                '}';
    }
}
