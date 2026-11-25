package com.vortex.entity;

import jakarta.persistence.*;

/**
 * 券商实体类
 * 对应数据库 brokers 表
 */
@Entity
@Table(name = "brokers")
public class Broker extends BaseEntity {

    /**
     * 券商名称（唯一）
     */
    @Column(name = "broker_name", nullable = false, unique = true, length = 100)
    private String brokerName;

    /**
     * 券商技术标识符（如 ibkr、tiger、futu），非空值唯一，可关联同步适配器。
     * 允许为空；是否支持同步取决于对应适配器是否已注册。
     */
    @Column(name = "broker_code", length = 50)
    private String brokerCode;

    /**
     * 所属国家/地区
     */
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    /** 券商简介，与实际账户的备注独立。 */
    @Column(name = "introduction", length = 500)
    private String introduction;

    // ============ Constructors ============

    public Broker() {
    }

    public Broker(String brokerName, String country) {
        this.brokerName = brokerName;
        this.country = country;
    }

    // ============ Getters and Setters ============

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + getId() +
                ", brokerName='" + brokerName + '\'' +
                ", brokerCode='" + brokerCode + '\'' +
                ", country='" + country + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
