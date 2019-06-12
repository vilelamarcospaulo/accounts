package controllers.response;

import utils.JsonUtils;

public abstract class ResponseObject {
    public String toJson() {
        return JsonUtils.writeToJson(this);
    }
}
