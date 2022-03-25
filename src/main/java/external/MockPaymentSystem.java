package external;

public class MockPaymentSystem implements PaymentSystem {
    public MockPaymentSystem(){

    }

    @Override
    public boolean processPayment (String buyerAccountEmail, String sellerAccountEmail,
                                      double transactionAmount){
        return processPayment(buyerAccountEmail, sellerAccountEmail, transactionAmount);
    }

    @Override
    public boolean processRefund (String buyerAccountEmail, String sellerAccountEmail,
                                  double transactionAmount){
        return processRefund(buyerAccountEmail, sellerAccountEmail, transactionAmount);
    }
}
