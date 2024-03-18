package com.example.compas_test;

import android.hardware.SensorEvent;

// Delta
public class Rotate_v2 {

  private boolean condition;

  public Rotate_v2() {
    condition = false;
  }

  public String inputTxt() {
    return "Sosiska";
  }

  public void setCondition() {
    if (!condition) {
      condition = true;
      return;
    }
    condition = false;
  }

  public boolean getCondition() {
    return condition;
  }

//  public String calculateRotation() throws InterruptedException {
//    curr = degree;
//    while (condition) {
//      prev = curr;
//      Thread.sleep(1000);
//      curr = degree;
//
//      if (curr - prev >= 45) {
//          return "Go Left!";
//      }
//
//      if (curr >= 225 && curr <= 359 && prev >= 0) {
//        if ((360 - prev + curr) >= 45) {
//          return "Go Left!";
//        }
//      }
//
//      if (prev - curr >= 45) {
//          return "Go Right!";
//      }
//
//      if (prev >= 225 && prev <= 359 && curr >= 0) {
//        if ((360 - prev - curr) >= 45) {
//          return "Go Right!";
//        }
//      }
//
//    }
//    return "Go Forward!";
//  }
}
