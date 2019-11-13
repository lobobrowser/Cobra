package org.cobraparser.ssl;

import org.cobraparser.util.HexDump;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SslCertificate {

    /**
     * SimpleDateFormat pattern for an ISO 8601 date
     */
    private static String ISO_8601_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";

    /**
     * Name of the entity this certificate is issued to
     */
    private final DName mIssuedTo;

    /**
     * Name of the entity this certificate is issued by
     */
    private final DName mIssuedBy;

    /**
     * Not-before date from the validity period
     */
    private final Date mValidNotBefore;

    /**
     * Not-after date from the validity period
     */
    private final Date mValidNotAfter;

    /**
     * The original source certificate, if available.
     */
    private final X509Certificate mX509Certificate;

    /**
     * Creates a new SSL certificate object from an X509 certificate
     * @param certificate X509 certificate
     */
    public SslCertificate(X509Certificate certificate) {
        this(certificate.getNotBefore(),
                certificate.getNotAfter(),
                certificate);
    }

    private SslCertificate(Date validNotBefore, Date validNotAfter,
            X509Certificate x509Certificate) {
        mIssuedTo = DName.createSubjectDName(x509Certificate);
        mIssuedBy = DName.createIssuerDName(x509Certificate);
        mValidNotBefore = cloneDate(validNotBefore);
        mValidNotAfter  = cloneDate(validNotAfter);
        mX509Certificate = x509Certificate;
    }

    /**
     * @return Not-before date from the certificate validity period or
     * "" if none has been set
     */
    public Date getValidNotBeforeDate() {
        return cloneDate(mValidNotBefore);
    }

    /**
     * @return Not-after date from the certificate validity period or
     * "" if none has been set
     */
    public Date getValidNotAfterDate() {
        return cloneDate(mValidNotAfter);
    }


    /**
     * @return Issued-to distinguished name or null if none has been set
     */
    public DName getIssuedTo() {
        return mIssuedTo;
    }

    /**
     * @return Issued-by distinguished name or null if none has been set
     */
    public DName getIssuedBy() {
        return mIssuedBy;
    }

    /**
     * @return The {@code X509Certificate} used to create this {@code SslCertificate}
     */
    public X509Certificate getX509Certificate() {
        return mX509Certificate;
    }

    private static String getSerialNumber(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            return "";
        }

        BigInteger serialNumber = x509Certificate.getSerialNumber();
        if (serialNumber == null) {
            return "";
        }

        return fingerprint(serialNumber.toByteArray());
    }

    private static String getDigest(X509Certificate x509Certificate, String algorithm) {
        if (x509Certificate == null) {
            return "";
        }

        try {
            byte[] bytes = x509Certificate.getEncoded();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(bytes);
            return fingerprint(digest);
        } catch (CertificateEncodingException|NoSuchAlgorithmException ignored) {
            return "";
        }
    }

    private static String fingerprint(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            HexDump.appendByteAsHex(sb, b, true);
            if (i+1 != bytes.length) {
                sb.append(':');
            }
        }

        return sb.toString();
    }

    /**
     * @return A string representation of this certificate for debugging
     */
    public String toString() {
        return ("Issued to: " + mIssuedTo.getDName() + ";\n"
                + "Issued by: " + mIssuedBy.getDName() + ";\n");
    }

    /**
     * Parse an ISO 8601 date converting ParseExceptions to a null result;
     */
    private static Date parseDate(String string) {
        try {
            return new SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Format a date as an ISO 8601 string, return "" for a null date
     */
    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat(ISO_8601_DATE_FORMAT).format(date);
    }

    /**
     * Clone a possibly null Date
     */
    private static Date cloneDate(Date date) {
        if (date == null) {
            return null;
        }

        return (Date) date.clone();
    }

}
