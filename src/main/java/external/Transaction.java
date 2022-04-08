package external;

public class Transaction {

    private String buyerEmail;
    private String sellerEmail;
    private double amount;

    public Transaction(String buyerEmail, String sellerEmail, double amount) {
        this.buyerEmail = buyerEmail;
        this.sellerEmail = sellerEmail;
        this.amount = amount;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public double getAmount() {
        return amount;
    }
}
