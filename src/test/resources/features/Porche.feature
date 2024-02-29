@regression @porche
  Feature: Validating Porche Prices

    @porchePriceValidation @ui
    Scenario: Validating Porsche Price
      Given user navigates to "https://www.porsche.com/usa/modelstart/all/?modelrange=718"
      When user stores the price and selects the model 718 Cayman
      Then user validates Base price is matched with listed price

    @porchePriceForEquipment @ui
    Scenario: Validating Porche Price For Equipment
      Given user navigates to "https://www.porsche.com/usa/modelstart/all/?modelrange=718"
      When user stores the price and selects the model 718 Cayman
      And user adds Power Sport Seats (forteen-way) with Memory Package
      Then user validates that Price For Equipment is added and price matches
