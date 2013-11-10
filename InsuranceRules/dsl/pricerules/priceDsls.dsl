#I'm a comment
[when]There is a client=$c:Client()
[when]- with a sportcar=sportCar==true
[when]- with a {val} car make="PORSCHE".equalsIgnoreCase( carMake )
[then]Set price to {val}=$c.setInsurancePrice(new java.math.BigDecimal({val}));
[then]Increase price by {val} percent= $c.setInsurancePrice( addPercent($c.getInsurancePrice(), {val}));