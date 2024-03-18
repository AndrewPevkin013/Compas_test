package com.example.compas_test;

import android.hardware.SensorEvent;

// Delta
public class Rotate_v1 {


  private boolean condition;

  public Rotate_v1() {
    condition = false;
  }

  public String inputTxt() {
    return "Chaek";
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
//      if (degree > 180 || degree < 0) {
//        degree -= 360;
//        }
//      //      new java.util.Timer().schedule(
////        new java.util.TimerTask() {
////          @Override
////          public void run() {
////            secondAngle = degree;
////          }
////        },
////        1000
////      );
//      Thread.sleep(1000);
//      curr = degree;
//
//      if ((prev <= 0 && prev >= -135) && (curr >= 0 && curr <= 135)
//        || (prev >= 0 && prev <= 135) && (curr <= 0 && curr >= -135)
//        || curr > prev) { // TODO: чет не пошло решение, все сводится к тем же рассчетам!
//        if (curr - prev >= 45) {
//          return "Go Left!";
//        }
//      }
//
//      if (Math.abs(curr) - Math.abs(prev) >= 45) {
//        return "Go Right!";
//      }
//
//    }
//    return "Go Forward!";
//  }
}
