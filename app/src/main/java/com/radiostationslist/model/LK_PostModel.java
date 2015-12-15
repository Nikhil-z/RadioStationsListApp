package com.radiostationslist.model;

/**
 * Created by dhananjay on 6/2/15.
 */
public class LK_PostModel {

    /**
     * SEREN_POSTModel class is used to hold POST Model Values
     */

    /**
     * SEREN_POSTModel Members Declarations
     */

    public String str_PostParamType;
    public String str_PostParamKey;
    public Object Obj_PostParamValue;

    /**
     * Get Method to Retrieve Post Param MIME Type
     *
     * @return String str_PostParamMIME
     */
    public String getStr_PostParamType() {
        return str_PostParamType;
    }

    /**
     * Set Method to Set Post Param MIME Type
     *
     * @param str_PostParamType MIME String
     */
    public void setStr_PostParamType(String str_PostParamType) {
        this.str_PostParamType = str_PostParamType;
    }

    /**
     * Get Method to Retrieve Post Param Key
     *
     * @return String str_PostParamKey
     */
    public String getStr_PostParamKey() {
        return str_PostParamKey;
    }

    /**
     * Set Method to Set Post Param Key
     *
     * @param str_PostParamKey String
     */
    public void setStr_PostParamKey(String str_PostParamKey) {
        this.str_PostParamKey = str_PostParamKey;
    }

    /**
     * Get Method to Retrieve Post Param Value
     *
     * @return Object Obj_PostParamValue
     */
    public Object getObj_PostParamValue() {
        return Obj_PostParamValue;
    }

    /**
     * Set Method to Set Post Param Value
     *
     * @param obj_PostParamValue Object
     */
    public void setObj_PostParamValue(Object obj_PostParamValue) {
        this.Obj_PostParamValue = obj_PostParamValue;
    }

}
