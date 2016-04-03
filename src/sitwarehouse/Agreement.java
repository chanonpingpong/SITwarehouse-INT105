package sitwarehouse;

public class Agreement {
    private long warehouseId;
    private double totalDepts;
    private String startDate;
    private String endDate;
    private double nextPaid;
    private String nPaidDate;
    private double arrears;

    public Agreement() {
    }

    public Agreement(long warehouseId, double totalDepts, String startDate, String endDate, double nextPaid, String nPaidDate, double arrears) {
        this.warehouseId = warehouseId;
        this.totalDepts = totalDepts;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nextPaid = nextPaid;
        this.nPaidDate = nPaidDate;
        this.arrears = arrears;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }
    

    public double getTotalDepts() {
        return totalDepts;
    }

    public void setTotalDepts(double totalDepts) {
        this.totalDepts = totalDepts;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getNextPaid() {
        return nextPaid;
    }

    public void setNextPaid(double nextPaid) {
        this.nextPaid = nextPaid;
    }

    public String getnPaidDate() {
        return nPaidDate;
    }

    public void setnPaidDate(String nPaidDate) {
        this.nPaidDate = nPaidDate;
    }

    public double getArrears() {
        return arrears;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }
    
    public void calcTotalDepts(double amount){
        totalDepts -= amount;
    }
    public void calcNextPaid(){
        nextPaid += arrears;
    }

    @Override
    public String toString() {
        return "Agreement{" + "warehouseId=" + warehouseId + ", totalDepts=" + totalDepts + ", startDate=" + startDate + ", endDate=" + endDate + ", nextPaid=" + nextPaid + ", nPaidDate=" + nPaidDate + ", arrears=" + arrears + '}';
    }
    
}
