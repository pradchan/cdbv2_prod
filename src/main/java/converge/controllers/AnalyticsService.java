package converge.controllers;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import converge.models.Analytics;

class AnalyticsService {
    private AnalyticsDao adao = new AnalyticsDao();


    public Analytics getAnalyticsData() throws Exception {
        Vector vct = new Vector();
        Analytics ant = new Analytics();
        Vector returnVector = new Vector();
        int indexCount = 0;
        try {

            vct = adao.getAllAnalyticsData();

            Vector productByCategory = (Vector) vct.get(0);
            if (productByCategory.size() > 0) {
                List<String> productList = (List<String>) productByCategory.get(0);
                String stringifiedProduct = getLstAsString(productList);
                ant.setCategoryForCatByCount(stringifiedProduct);
                ant.setCategoryForCatByCountList(productList);
                ant.setCountForCatByCount((List<String>) productByCategory.get(1));

            }
            if (vct.get(1) != null) {
                int totalCount = (Integer) vct.get(1);
                ant.setTotalProductCount(totalCount);
            }


            Vector prdCntByPrice = (Vector) vct.get(2);
            if (prdCntByPrice.size() > 2) {
                ant.setPriceForPrdCountByPrice((List<String>) prdCntByPrice.get(0));
                ant.setCountForPrdCountByPrice((List<String>) prdCntByPrice.get(1));
            }


            Vector resultByGender = (Vector) vct.get(3);
            for (int i = 0; i < resultByGender.size(); i++) {
                String temp = getLstAsString(((List<String>) resultByGender.get(0)));
                ant.setNameForSalesByGender(temp);
                ant.setMaleCountForSalesByGender((List<String>) resultByGender.get(1));
                ant.setFemaleCountForSalesByGender((List<String>) resultByGender.get(2));


            }
            Vector revenueOnSales = (Vector) vct.get(4);
            if (revenueOnSales.size() > 0) {
                ant.setProductNameForTopFiveRevenueSales((List<String>) revenueOnSales.get(0));
                ant.setNumberOfOrdersForTopFiveRevenueSales((List<String>) revenueOnSales.get(1));
                ant.setTotalValueForTopFiveRevenueSales((List<String>) revenueOnSales.get(2));
                ant.setRevenueRankForTopFiveRevenueSales((List<String>) revenueOnSales.get(3));
            }

            Vector revenueByDate = (Vector) vct.get(5);
            for (int i = 0; i < revenueByDate.size(); i++) {
                String stringifiedValue = getLstAsString((List<String>) revenueByDate.get(0));
                ant.setDatesForGetRevenueByDate(stringifiedValue);
                ant.setValueOfOrdersForGetRevenueByDate((List<String>) revenueByDate.get(2));
            }
        } catch (Exception e) {
            throw e;
        }

        return ant;
    }


    public String getLstAsString(List li) {
        String temp = "[";


        for (int t = 0; t < li.size(); t++) {
            temp += "\"" + li.get(t) + "\"";


            if (t < li.size() - 1) {
                temp += ", ";

            }

        }

        temp += "]";
        return temp;

    }

}
