package converge.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Analytics {

    //getProductCountByCategory
    String categoryForCatByCount;
    List<String> categoryForCatByCountList;

    List<String> countForCatByCount;

    //getTotalProductCount
    private int totalProductCount;

    //getProductCountByPrice
    List<String> countForPrdCountByPrice;
    List<String> priceForPrdCountByPrice;


    //getSalesByGender
    String nameForSalesByGender;
    List<String> maleCountForSalesByGender;
    List<String> femaleCountForSalesByGender;


    //getTopFiveRevenueSales
    List<String> productNameForTopFiveRevenueSales;
    List<String> numberOfOrdersForTopFiveRevenueSales;
    List<String> totalValueForTopFiveRevenueSales;
    List<String> revenueRankForTopFiveRevenueSales;


    //getRevenueByDate
    String datesForGetRevenueByDate;
    List<String> numberOfOrdersForGetRevenueByDate;
    List<String> valueOfOrdersForGetRevenueByDate;


    public List<String> getCategoryForCatByCountList() {
        return categoryForCatByCountList;
    }

    public void setCategoryForCatByCountList(List<String> categoryForCatByCountList) {
        this.categoryForCatByCountList = categoryForCatByCountList;
    }


    public String getCategoryForCatByCount() {
        return categoryForCatByCount;
    }

    public void setCategoryForCatByCount(String categoryForCatByCount) {
        this.categoryForCatByCount = categoryForCatByCount;
    }

    public List<String> getCountForCatByCount() {
        return countForCatByCount;
    }

    public void setCountForCatByCount(List<String> countForCatByCount) {
        this.countForCatByCount = countForCatByCount;
    }

    public int getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public List<String> getCountForPrdCountByPrice() {
        return countForPrdCountByPrice;
    }

    public void setCountForPrdCountByPrice(List<String> countForPrdCountByPrice) {
        this.countForPrdCountByPrice = countForPrdCountByPrice;
    }

    public List<String> getPriceForPrdCountByPrice() {
        return priceForPrdCountByPrice;
    }

    public void setPriceForPrdCountByPrice(List<String> priceForPrdCountByPrice) {
        this.priceForPrdCountByPrice = priceForPrdCountByPrice;
    }

    public String getNameForSalesByGender() {
        return nameForSalesByGender;
    }

    public void setNameForSalesByGender(String nameForSalesByGender) {
        this.nameForSalesByGender = nameForSalesByGender;
    }

    public List<String> getMaleCountForSalesByGender() {
        return maleCountForSalesByGender;
    }

    public void setMaleCountForSalesByGender(List<String> maleCountForSalesByGender) {
        this.maleCountForSalesByGender = maleCountForSalesByGender;
    }

    public List<String> getFemaleCountForSalesByGender() {
        return femaleCountForSalesByGender;
    }

    public void setFemaleCountForSalesByGender(List<String> femaleCountForSalesByGender) {
        this.femaleCountForSalesByGender = femaleCountForSalesByGender;
    }

    public List<String> getProductNameForTopFiveRevenueSales() {
        return productNameForTopFiveRevenueSales;
    }

    public void setProductNameForTopFiveRevenueSales(List<String> productNameForTopFiveRevenueSales) {
        this.productNameForTopFiveRevenueSales = productNameForTopFiveRevenueSales;
    }

    public List<String> getNumberOfOrdersForTopFiveRevenueSales() {
        return numberOfOrdersForTopFiveRevenueSales;
    }

    public void setNumberOfOrdersForTopFiveRevenueSales(List<String> numberOfOrdersForTopFiveRevenueSales) {
        this.numberOfOrdersForTopFiveRevenueSales = numberOfOrdersForTopFiveRevenueSales;
    }

    public List<String> getTotalValueForTopFiveRevenueSales() {
        return totalValueForTopFiveRevenueSales;
    }

    public void setTotalValueForTopFiveRevenueSales(List<String> totalValueForTopFiveRevenueSales) {
        this.totalValueForTopFiveRevenueSales = totalValueForTopFiveRevenueSales;
    }

    public List<String> getRevenueRankForTopFiveRevenueSales() {
        return revenueRankForTopFiveRevenueSales;
    }

    public void setRevenueRankForTopFiveRevenueSales(List<String> revenueRankForTopFiveRevenueSales) {
        this.revenueRankForTopFiveRevenueSales = revenueRankForTopFiveRevenueSales;
    }

    public String getDatesForGetRevenueByDate() {
        return datesForGetRevenueByDate;
    }

    public void setDatesForGetRevenueByDate(String datesForGetRevenueByDate) {
        this.datesForGetRevenueByDate = datesForGetRevenueByDate;
    }

    public List<String> getNumberOfOrdersForGetRevenueByDate() {
        return numberOfOrdersForGetRevenueByDate;
    }

    public void setNumberOfOrdersForGetRevenueByDate(List<String> numberOfOrdersForGetRevenueByDate) {
        this.numberOfOrdersForGetRevenueByDate = numberOfOrdersForGetRevenueByDate;
    }

    public List<String> getValueOfOrdersForGetRevenueByDate() {
        return valueOfOrdersForGetRevenueByDate;
    }

    public void setValueOfOrdersForGetRevenueByDate(List<String> valueOfOrdersForGetRevenueByDate) {
        this.valueOfOrdersForGetRevenueByDate = valueOfOrdersForGetRevenueByDate;
    }


}
