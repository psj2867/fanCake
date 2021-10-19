package ml.psj2867.fancake.entity.type;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VideoAutctionState {
   CANCEL(false)
  ,SUCCESS(true)
  ;

  private boolean success;

  public boolean isSuccess(){
    return this.success;
  }


  public static Optional<VideoAutctionState> of(String name){
    try {      
      return Optional.ofNullable( VideoAutctionState.valueOf(name) );
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
