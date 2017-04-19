package com.youhujia.solar.domain.department;

import com.youhujia.solar.domain.organization.Organization;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by huangYing on 2017/4/17.
 */
@Entity
public class Department {
    private Long id;
    private Long organizationId;
    private Boolean isGuest;
    private Long hostId;
    private String name;
    private String number;
    private String authCode;
    private Byte status;
    private String mayContact;
    private String imgUrl;
    private String wxSubQrCodeValue;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String classificationType;

    private Organization organizationByOrganizationId;
    private Department departmentByHostId;
    private Collection<Department> departmentsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "organization_id", nullable = false)
    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Basic
    @Column(name = "is_guest", nullable = false)
    public Boolean getGuest() {
        return isGuest;
    }

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    @Basic
    @Column(name = "host_id", nullable = false)
    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 512)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "number", nullable = true, length = 512)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "auth_code", nullable = true, length = 512)
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Basic
    @Column(name = "status", insertable = false, nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "may_contact", nullable = true)
    public String getMayContact() {
        return mayContact;
    }

    public void setMayContact(String mayContact) {
        this.mayContact = mayContact;
    }

    @Basic
    @Column(name = "img_url", nullable = true)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "wx_sub_qr_code_value", nullable = true)
    public String getWxSubQRCodeValue() {
        return wxSubQrCodeValue;
    }

    public void setWxSubQRCodeValue(String wxSubQrCodeValue) {
        this.wxSubQrCodeValue = wxSubQrCodeValue;
    }

    @Basic
    @Column(name = "created_at", insertable = false, updatable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", insertable = false, updatable = false)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "classification_type", nullable = true)
    public String getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(String classificationType) {
        this.classificationType = classificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (organizationId != null ? !organizationId.equals(that.organizationId) : that.organizationId != null)
            return false;
        if (isGuest != null ? !isGuest.equals(that.isGuest) : that.isGuest != null) return false;

        if (id != that.id) return false;
        if (organizationId != that.organizationId) return false;
        if (isGuest != that.isGuest) return false;
        if (status != that.status) return false;
        if (hostId != null ? !hostId.equals(that.hostId) : that.hostId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (authCode != null ? !authCode.equals(that.authCode) : that.authCode != null) return false;
        if (mayContact != null ? !mayContact.equals(that.mayContact) : that.mayContact != null) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
        if (wxSubQrCodeValue != null ? !wxSubQrCodeValue.equals(that.wxSubQrCodeValue) : that.wxSubQrCodeValue != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (classificationType != null ? !classificationType.equals(that.classificationType) : that.classificationType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (organizationId ^ (organizationId >>> 32));
        result = 31 * result + (isGuest ? 1 : 0);
        result = 31 * result + (hostId != null ? hostId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (authCode != null ? authCode.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + (mayContact != null ? mayContact.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (wxSubQrCodeValue != null ? wxSubQrCodeValue.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (classificationType != null ? classificationType.hashCode() : 0);

        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Organization getOrganizationByOrganizationId() {
        return organizationByOrganizationId;
    }

    public void setOrganizationByOrganizationId(Organization organizationByOrganizationId) {
        this.organizationByOrganizationId = organizationByOrganizationId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Department getDepartmentByHostId() {
        return departmentByHostId;
    }

    public void setDepartmentByHostId(Department departmentByHostId) {
        this.departmentByHostId = departmentByHostId;
    }

    @OneToMany(mappedBy = "departmentByHostId", fetch = FetchType.LAZY)
    public Collection<Department> getDepartmentsById() {
        return departmentsById;
    }

    public void setDepartmentsById(Collection<Department> departmentsById) {
        this.departmentsById = departmentsById;
    }

}

