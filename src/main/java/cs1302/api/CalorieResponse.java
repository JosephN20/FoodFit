package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a response from the
 * Fitness Calculator API.
 * This is used by Gson to
 * create an object from the JSON response body.
 */

public class CalorieResponse {

    /**
     * Class for Data object.
     */
    public class Data {

        /**
         * Class for Goals object.
         */
        public class Goals {

            /**
             * Class for MildWeightLoss object.
             */
            public class MildWeightLoss {
                Double calory;

                @Override
                public String toString() {
                    return calory.toString();
                }
            } // MildWeightLoss

            /**
             * Class for MildWeightGain object.
             */
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

                return "mainWeight:" + (maintainWeight != null ? maintainWeight.toString() : "")
                    + "mildWeightLoss" + (mildWeightLoss != null ? mildWeightLoss.toString() : "")
                    + "mildWeightGrowth:"
                    + (mildWeightGain != null ? mildWeightGain.toString() : "");
            } // toString

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

    /**
     * Set the data in the API to data.
     * @param data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Set the status Code in the API to status Code.
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Set the request result in the API to request result.
     * @param requestResult
     */
    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }

    @Override
    public String toString() {
        return statusCode + requestResult.toString() + data.toString();
    }


} // CalorieResponse
