package org.cobraparser.ssl;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/**
 * A distinguished name helper class: a 3-tuple of:
 * <ul>
 *   <li>the most specific common name (CN)</li>
 *   <li>the most specific organization (O)</li>
 *   <li>the most specific organizational unit (OU)</li>
 * <ul>
 */
public class DName {
    /**
     * Distinguished name (normally includes CN, O, and OU names)
     */
    private String mDName;

    /**
     * Common-name (CN) component of the name
     */
    private String mCName;

    /**
     * Organization (O) component of the name
     */
    private String mOName;

    /**
     * Organizational Unit (OU) component of the name
     */
    private String mUName;

    /**
     * Creates a new {@code DName} from a string. The attributes
     * are assumed to come in most significant to least
     * significant order which is true of human readable values
     * returned by methods such as {@code X500Principal.getName()}.
     * Be aware that the underlying sources of distinguished names
     * such as instances of {@code X509Certificate} are encoded in
     * least significant to most significant order, so make sure
     * the value passed here has the expected ordering of
     * attributes.
     */
    private DName(X509Certificate mCertificate, boolean subject) {
        try {
            X500Name x500Name;

            if (subject) {
                x500Name = new JcaX509CertificateHolder(mCertificate).getSubject();
            } else {
                x500Name = new JcaX509CertificateHolder(mCertificate).getIssuer();
            }

            RDN dName = x500Name.getRDNs(BCStyle.DN_QUALIFIER)[0];
            RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
            RDN organization = x500Name.getRDNs(BCStyle.O)[0];
            RDN ou = x500Name.getRDNs(BCStyle.OU)[0];

            this.mDName = IETFUtils.valueToString(dName.getFirst().getValue());
            this.mCName = IETFUtils.valueToString(cn.getFirst().getValue());
            this.mOName = IETFUtils.valueToString(organization.getFirst().getValue());
            this.mUName = IETFUtils.valueToString(ou.getFirst().getValue());
        } catch (CertificateEncodingException e) {
            // thrown here if there is an exception.
        }
    }

    public static DName createSubjectDName(X509Certificate mCertificate) {
        return new DName(mCertificate, true);
    }

    public static DName createIssuerDName(X509Certificate mCertificate) {
        return new DName(mCertificate, false);
    }

    /**
     * @return The distinguished name (normally includes CN, O, and OU names)
     */
    public String getDName() {
        return mDName != null ? mDName : "";
    }

    /**
     * @return The most specific Common-name (CN) component of this name
     */
    public String getCName() {
        return mCName != null ? mCName : "";
    }

    /**
     * @return The most specific Organization (O) component of this name
     */
    public String getOName() {
        return mOName != null ? mOName : "";
    }

    /**
     * @return The most specific Organizational Unit (OU) component of this name
     */
    public String getUName() {
        return mUName != null ? mUName : "";
    }
}
