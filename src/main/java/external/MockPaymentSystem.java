package external;

import java.util.ArrayList;
import java.util.List;

public class MockPaymentSystem implements PaymentSystem {

    private List<Transaction> transactions;

    public MockPaymentSystem(){
        this.transactions = new ArrayList<>();
    }

    @Override
    public boolean processPayment (String buyerAccountEmail, String sellerAccountEmail,
                                      double transactionAmount){
        // add transaction from buyer to seller
        Transaction newTransaction = new Transaction(buyerAccountEmail,
                sellerAccountEmail,
                transactionAmount);
        transactions.add(newTransaction);

        return true;
    }

    @Override
    public boolean processRefund (String buyerAccountEmail, String sellerAccountEmail,
                                  double transactionAmount){

        // return money from seller to buyer

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() == transactionAmount &&
            buyerAccountEmail.equals(transaction.getBuyerEmail()) &&
            sellerAccountEmail.equals(transaction.getSellerEmail())) {
                transactions.remove(transaction);
                return true;
            }
        }

        return false;
    }
}
