package com.example.expensetracker;

public class ExpenseItem {
    private String name;
    private String category;
    private String date;
    private Float amount;
    private String note;

    public ExpenseItem(String pname, String pcat, String pdate, Float pamount, String pnote){
        name  = pname;
        category = pcat;
        date = pdate;
        amount = pamount;
        note = pnote;
    }

    public ExpenseItem(ExpenseItem ei){
        name = ei.getName();
        category = ei.getCategory();
        date = ei.getDate();
        amount = ei.getAmount();
        note = ei.getNote();
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;
    }

    public Float getAmount(){
        return amount;
    }

    public String getNote(){
        return note;
    }

    public void setName(String n){
        name = n;
    }

    public void setCategory(String c){
        category = c;
    }

    public void setDate(String d){
        date = d;
    }

    public void setAmount(Float a){
        amount = a;
    }

    public void setNote(String n){
        note = n;
    }

    public void changeValues(ExpenseItem ei){
        name = ei.getName();
        category = ei.getCategory();
        date = ei.getDate();
        amount = ei.getAmount();
        note = ei.getNote();
    }
}

