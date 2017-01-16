package com.reijn.easydrive;

import java.io.Serializable;

/**
 * Created by reijn on 29-12-2016.
 */



public class Transport implements Serializable {
    private String id, date, company, transportNumber, plate, origin, destination;
    private String startTime, startAddress, arrivalOrigin, departureOrigin,
            arrivalDestination, keysTime, keysToo, secondTransport, finalAddress, finalStation, finalTime;
    private String cardUsed, fuelCosts, fuelPic, carwashCosts, carwashPic, cleaningCosts, cleaningPicture, ovCosts, ovPic, otherCosts, otherPic, notes,
            delay, delayLocation, delayTime;

    Transport() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    void setCompany(String company) {
        this.company = company;
    }

    String getTransportNumber() {
        return transportNumber;
    }

    void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public String getPlate() {
        return plate;
    }

    void setPlate(String plate) {
        this.plate = plate;
    }

    String getOrigin() {
        return origin;
    }

    void setOrigin(String origin) {
        this.origin = origin;
    }

    String getDestination() {
        return destination;
    }

    void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartTime() {
        return startTime;
    }

    void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getArrivalOrigin() {
        return arrivalOrigin;
    }

    void setArrivalOrigin(String arrivalOrigin) {
        this.arrivalOrigin = arrivalOrigin;
    }

    public String getDepartureOrigin() {
        return departureOrigin;
    }

    void setDepartureOrigin(String departureOrigin) {
        this.departureOrigin = departureOrigin;
    }

    public String getArrivalDestination() {
        return arrivalDestination;
    }

    void setArrivalDestination(String arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public String getKeysTime() {
        return keysTime;
    }

    void setKeysTime(String keysTime) {
        this.keysTime = keysTime;
    }

    public String getKeysToo() {
        return keysToo;
    }

    void setKeysToo(String keysToo) {
        this.keysToo = keysToo;
    }

    public String getSecondTransport() {
        return secondTransport;
    }

    void setSecondTransport(String secondTransport) {
        this.secondTransport = secondTransport;
    }

    public String getFinalAddress() {
        return finalAddress;
    }

    void setFinalAddress(String finalAddress) {
        this.finalAddress = finalAddress;
    }

    public String getFinalStation() {
        return finalStation;
    }

    void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public String getFinalTime() {
        return finalTime;
    }

    void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getCardUsed() {
        return cardUsed;
    }

    void setCardUsed(String cardUsed) {
        this.cardUsed = cardUsed;
    }

    public String getFuelCosts() {
        return fuelCosts;
    }

    void setFuelCosts(String fuelCosts) {
        this.fuelCosts = fuelCosts;
    }

    public String getFuelPic() {
        return fuelPic;
    }

    public void setFuelPic(String fuelPic) {
        this.fuelPic = fuelPic;
    }

    public String getCarwashCosts() {
        return carwashCosts;
    }

    void setCarwashCosts(String carwashCosts) {
        this.carwashCosts = carwashCosts;
    }

    public String getCarwashPic() {
        return carwashPic;
    }

    public void setCarwashPic(String carwashPic) {
        this.carwashPic = carwashPic;
    }

    public String getCleaningCosts() {
        return cleaningCosts;
    }

    void setCleaningCosts(String cleaningCosts) {
        this.cleaningCosts = cleaningCosts;
    }

    public String getCleaningPicture() {
        return cleaningPicture;
    }

    public void setCleaningPicture(String cleaningPicture) {
        this.cleaningPicture = cleaningPicture;
    }

    public String getOvCosts() {
        return ovCosts;
    }

    void setOvCosts(String ovCosts) {
        this.ovCosts = ovCosts;
    }

    public String getOvPic() {
        return ovPic;
    }

    public void setOvPic(String ovPic) {
        this.ovPic = ovPic;
    }

    public String getOtherCosts() {
        return otherCosts;
    }

    void setOtherCosts(String otherCosts) {
        this.otherCosts = otherCosts;
    }

    public String getOtherPic() {
        return otherPic;
    }

    public void setOtherPic(String otherPic) {
        this.otherPic = otherPic;
    }

    public String getNotes() {
        return notes;
    }

    void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDelay() {
        return delay;
    }

    void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDelayLocation() {
        return delayLocation;
    }

    void setDelayLocation(String delayLocation) {
        this.delayLocation = delayLocation;
    }

    public String getDelayTime() {
        return delayTime;
    }

    void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }
}
