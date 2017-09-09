package model;
/**
 * Node information for a specific node in a specifing epoch;
 * 
 * @author jubatyrn
 */

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeInfo {
    private int id;
    private int epoch;
    
    private double avg;
    private double sum;
    private double min;
    private double max;
    private double count;
    private double sumsqr;
    private double stdev;
    private double state;
    
    private ArrayList<Integer> push;
    private ArrayList<Integer> pull;
    private ArrayList<Integer> leave;
    private ArrayList<Integer> return_;
    private ArrayList<Integer> pss0;
    private ArrayList<Integer> pss1;
    private ArrayList<Integer> pss2;
    private ArrayList<Integer> pss3;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getEpoch() {
        return epoch;
    }
    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }
    public double getAvg() {
        return avg;
    }
    public void setAvg(double avg) {
        this.avg = avg;
    }
    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public double getCount() {
        return count;
    }
    public void setCount(double count) {
        this.count = count;
    }
    public double getSumsqr() {
        return sumsqr;
    }
    public void setSumsqr(double sumsqr) {
        this.sumsqr = sumsqr;
    }
    public double getStdev() {
        return stdev;
    }
    public void setStdev(double stdev) {
        this.stdev = stdev;
    }
    public double getState() {
        return state;
    }
    public void setState(double state) {
        this.state = state;
    }
    
    @JsonProperty("PUSH")
    public ArrayList<Integer> getPush() {
        return push;
    }
    @JsonProperty("PUSH")
    public void setPUSH(ArrayList<Integer> pUSH) {
        push = pUSH;
    }
    @JsonProperty("PULL")
    public ArrayList<Integer> getPull() {
        return pull;
    }
    @JsonProperty("PULL")
    public void setPULL(ArrayList<Integer> pULL) {
        pull = pULL;
    }
    @JsonProperty("LEAVE")
    public ArrayList<Integer> getLeave() {
        return leave;
    }
    @JsonProperty("LEAVE")
    public void setLEAVE(ArrayList<Integer> lEAVE) {
        leave = lEAVE;
    }
    @JsonProperty("RETURN")
    public ArrayList<Integer> getReturn() {
        return return_;
    }
    @JsonProperty("RETURN")
    public void setRETURN(ArrayList<Integer> rETURN) {
        return_ = rETURN;
    }
    public ArrayList<Integer> getPss0() {
        return pss0;
    }
    public void setPss0(ArrayList<Integer> pss0) {
        this.pss0 = pss0;
    }
    public ArrayList<Integer> getPss1() {
        return pss1;
    }
    public void setPss1(ArrayList<Integer> pss1) {
        this.pss1 = pss1;
    }
    public ArrayList<Integer> getPss2() {
        return pss2;
    }
    public void setPss2(ArrayList<Integer> pss2) {
        this.pss2 = pss2;
    }
    public ArrayList<Integer> getPss3() {
        return pss3;
    }
    public void setPss3(ArrayList<Integer> pss3) {
        this.pss3 = pss3;
    }
    public String toString() {
        return "Epoch:" + epoch + ", Node:" + id + "\n";
    }
}
