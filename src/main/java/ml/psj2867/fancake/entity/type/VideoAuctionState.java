package ml.psj2867.fancake.entity.type;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VideoAuctionState {
   CANCEL(false)
  ,SUCCESS(true)
  ;

  private boolean success;

  public boolean isSuccess(){
    return this.success;
  }


  public static Optional<VideoAuctionState> of(String name){
    try {      
      return Optional.ofNullable( VideoAuctionState.valueOf(name) );
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
