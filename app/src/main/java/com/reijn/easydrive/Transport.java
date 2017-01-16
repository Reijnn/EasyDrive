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

    public Transport() {
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getArrivalOrigin() {
        return arrivalOrigin;
    }

    public void setArrivalOrigin(String arrivalOrigin) {
        this.arrivalOrigin = arrivalOrigin;
    }

    public String getDepartureOrigin() {
        return departureOrigin;
    }

    public void setDepartureOrigin(String departureOrigin) {
        this.departureOrigin = departureOrigin;
    }

    public String getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(String arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public String getKeysTime() {
        return keysTime;
    }

    public void setKeysTime(String keysTime) {
        this.keysTime = keysTime;
    }

    public String getKeysToo() {
        return keysToo;
    }

    public void setKeysToo(String keysToo) {
        this.keysToo = keysToo;
    }

    public String getSecondTransport() {
        return secondTransport;
    }

    public void setSecondTransport(String secondTransport) {
        this.secondTransport = secondTransport;
    }

    public String getFinalAddress() {
        return finalAddress;
    }

    public void setFinalAddress(String finalAddress) {
        this.finalAddress = finalAddress;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getCardUsed() {
        return cardUsed;
    }

    public void setCardUsed(String cardUsed) {
        this.cardUsed = cardUsed;
    }

    public String getFuelCosts() {
        return fuelCosts;
    }

    public void setFuelCosts(String fuelCosts) {
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

    public void setCarwashCosts(String carwashCosts) {
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

    public void setCleaningCosts(String cleaningCosts) {
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

    public void setOvCosts(String ovCosts) {
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

    public void setOtherCosts(String otherCosts) {
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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDelayLocation() {
        return delayLocation;
    }

    public void setDelayLocation(String delayLocation) {
        this.delayLocation = delayLocation;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }
}
