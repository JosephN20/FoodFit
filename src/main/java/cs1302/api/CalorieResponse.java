package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a response from the 
 * Fitness Calculator API. 
 * This is used by Gson to
 * create an object from the JSON response body. 
 */

public class CalorieResponse {


    public class Data {
        public class Goals {
            
            public class MildWeightLoss {
                Double calory;

                @Override
                public String toString() {
                    return calory.toString();
                }
            } // MildWeightLoss

            public class MildWeigthGain {
                Double calory;

                @Override
                public String toString() {
                    return calory.toString();
                }
            } // MildWeightGain

            @SerializedName("maintain weight")
            Double maintainWeight;
            @SerializedName("Mild weight loss")
            MildWeightLoss mildWeightLoss;
            @SerializedName("Mild weight gain")
            MildWeigthGain mildWeightGain;

            @Override
            public String toString() {
             
                return "mainWeight:" + (maintainWeight != null ? maintainWeight.toString() : "")  + "mildWeightLoss" + (mildWeightLoss != null ? mildWeightLoss.toString() : "") + "mildWeightGrowth:" + (mildWeightGain != null ? mildWeightGain.toString() : "");
            }

        } // Goals
        @SerializedName("goals")
        Goals goals;

        @Override
        public String toString() {
            return goals.toString();
        }
    } //Data
    @SerializedName("data")
    Data data;
    @SerializedName("status_code")
    int statusCode;
    @SerializedName("request_result")
    String requestResult;

    public void setData(Data data) {
        this.data = data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }

    @Override
    public String toString() {
        return statusCode + requestResult.toString() + data.toString();
    }


} // CalorieResponse






