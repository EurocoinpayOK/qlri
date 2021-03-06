package commands.param.validators;

import commands.param.ParameterValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectValidator extends ParameterValidator {

    @Override
    public String validate(String par) {
        try {
            new JSONObject(par);
            return null;
        } catch (JSONException e) {
            return "not a json object";
        }
    }

    @Override
    public Object convertParToObject(String par) {
        return new JSONObject(par);
    }

    @Override
    public String toString() {
        return "JSONObject";
    }

    @Override
    public String genJSValidation() {
        return "_ParameterValidator.validate_object("+getJSONKey()+", '"+getJSONKey()+"');";
    }

    @Override
    public String genPHPValidation() {
        return "$this->validate_object($"+getJSONKey()+", '$"+getJSONKey()+"');";
    }

    @Override
    public String getJSType() {
        return "object";
    }
}