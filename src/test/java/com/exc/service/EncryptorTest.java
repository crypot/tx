package com.exc.service;

import org.junit.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptorTest {


    @Test
    public void testEncryptDecrypt() throws Exception {
        EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        ECKeyPair pair = Keys.createEcKeyPair();
        // String addr = Keys.getAddress(pair);
        String pk = Numeric.toHexStringWithPrefixZeroPadded(pair.getPrivateKey(), Keys.PRIVATE_KEY_LENGTH_IN_HEX);

        assertThat(pk).isEqualTo(encryptDecrypt.decrypt(encryptDecrypt.encrypt(pk)));
        /*Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
        Transaction transaction = Transaction.createEtherTransaction()
        TransactionReceipt transactionReceipt = Transfer.sendFunds(
            web3, credentials, "0x<address>|<ensName>",
            BigDecimal.valueOf(1.0), Convert.Unit.ETHER)
            .send();*/
    }
}
