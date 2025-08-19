Feature: Booking Page UI and Functional Validation

@success
Scenario: Submit form with all valid data
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid details:
	| Name     | Phone      | Email         | Trip | Cab  | CabType | Date       | Time  | Passenger | TripType |
	| Mouli | 9876543210 | mouli@test.com | long | Mini | AC      | 2025-07-28 | 09:30 | 2         | oneway   |
	And User clicks on Book Now button
	Then Booking confirmation message "Your Booking has been Confirmed" should be displayed

@errordetected
Scenario: Submit form with missing name
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid details:
	| Name | Phone      | Email         | Trip | Cab  | CabType | Date       | Time  | Passenger | TripType |
	|      | 9876543210 | mouli@test.com | long | Mini | AC      | 2025-07-28 | 09:30 | 2         | oneway |
	And User clicks on Book Now button
	Then Error message "Please enter the name" should be shown under Name field

@knownbug
Scenario: Error message for invalid data should be shown under "<Field>" field 
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid details:
	| Name   | Phone      | Email        | Trip   | Cab   | CabType   | Date       | Time   | Passenger   | TripType   |
	| <Name> | <Phone>    | <Email>      | <Trip> | <Cab> | <CabType> | <Date>     | <Time> | <Passenger> | <TripType> |
	And User clicks on Book Now button
	Then Error message "<ExpectedError>" should be shown under "<Field>" field
	
Examples:
| Name  | Phone      | Email          | Trip | Cab     | CabType | Date       | Time     | Passenger | TripType | Field       | ExpectedError |
| 12345 | 9876543210 | valid@mail.com | long | Mini    | AC      | 2025-07-30 | 09:30    | 2         | oneway   | name        | Please enter a valid name |
| Anurag | 9876543210 | testmail.com   | long | Mini    | AC      | 2025-07-30 | 09:30    | 2         | oneway   | email       | Please enter a valid email |
| Anurag | consumer   | valid@mail.com | long | Mini    | AC      | 2025-07-30 | 09:30    | 2         | oneway   | phone       | Please enter a valid phone number |
| Anurag | 9876543210 | valid@mail.com | long | Mini    | AC      | 2025-07-30 | 25:67:92 | 2         | oneway   | pickup time | Please enter a valid pickup time  |
	
@errordetected
Scenario: Submit form with missing trip type
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid details:
	| Name  | Phone      | Email          | Trip | Cab  | CabType | Date       | Time  | Passenger | TripType |
	| Aniket | 9876543210 | aniket@test.com |      | Mini | AC      | 2025-07-28 | 09:30 | 2         | oneway |
	And User clicks on Book Now button
	Then Error message "Please Select the Trip" should be shown under Trip selection
@errordetected
Scenario: Submit form with missing number of passengers
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid details:
	| Name | Phone      | Email         | Trip  | Cab  | CabType | Date       | Time  | Passenger | TripType |
	| Paarth | 9876543210 | paarth@test.com | local | Mini | AC      | 2025-07-28 | 09:30 |           | oneway |
	And User clicks on Book Now button
	Then Error message "Please Select the number of passengers" should be shown under Passenger count

@exceldata
Scenario: Booking with row 0 from Excel data
	Given User reads booking data from Excel sheet "BookingData" and row 0
	When User fills the form using Excel data
	And User clicks on Book Now button
	Then Booking confirmation message "Your Booking has been Confirmed" should be displayed
@exceldata
Scenario: Booking with row 1 from Excel data
	Given User reads booking data from Excel sheet "BookingData" and row 1
	When User fills the form using Excel data
	And User clicks on Book Now button
	Then Email error message "Please enter the email" should be displayed