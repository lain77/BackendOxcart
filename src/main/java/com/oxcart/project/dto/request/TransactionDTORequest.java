package com.oxcart.project.dto.request;

import com.oxcart.project.dto.response.TransactionDTOResponse;

public class TransactionDTORequest {

    private int id;

    private String timestamp;

    private int type;

    private Integer userId;

    private Integer cardId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCardId() {
        return cardId;
    }
}
