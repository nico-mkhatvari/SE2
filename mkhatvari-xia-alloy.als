
enum Privacy {PUBLIC, PRIVATE}
enum EventType {OUTDOOR, INDOOR}

sig Num{

	value: Int

}

sig InvitationList{

	guest: some User,

}

sig Calendar{

	owner: one User,
	visibility: one Privacy,

}

sig User {

	id: one Int,
	viewCal: some Calendar,
		
}

sig Notification{

	sendTo: InvitationList,

}

sig ForecastWeather{

	zip: one Num,
	update:  Int,
	fcast: one Event,
	
}

sig Event{

	eo: one User, 
	date:  one Int,
	etype: one EventType,
	eprivacy: one Privacy,
	invit: InvitationList,
	send: set Notification,

}{ date > 0 }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


fact no2user{

//non ci sono due User uguali
	no disj u, u': User | u.id = u'.id

}

fact forecastWeather{

//non esistono due previsioni meteo diverse per la stessa località e per lo stesso giorno
	no disj f, f':ForecastWeather | f.update = f'.update and f.zip = f'.zip
	
//previsioni meteo solo per eventi outdoor
	all f: ForecastWeather | no e: Event | f.fcast in e iff e.etype = INDOOR
	
}

 fact noticationFacts{
	
//non esiste un evento con una notifica non collegata ad un evento
	no n: Notification | all e: Event | n !in e.send 

//non esistono due eventi con la stessa notifica 
	no disj n, n' : Notification | all e: Event | n = e.send and n' = e.send
	
//ogni evento creato è in una invitation list 
	all e: Event |e.send.sendTo in e.invit

	all f: ForecastWeather| one e: Event |  #e.send >=#f.fcast 
	
}

fact calendarFacts{

// ogni utente puo vedere i calendari pubblici
	all c: Calendar | some u:User | (c in u.viewCal and c.visibility = PUBLIC) or (u in c.owner)

//utente owner puo vedere il proprio calendario
	all c: Calendar | c.owner in viewCal.c

//non esistono due eventi con le stesse caratteristiche chiave
	no disj e, e': Event | e'.date = e.date and e.eo=e'.eo

//per utente esiste uno e uno solo calendario
	all u: User | one c: Calendar | u = c.owner

}


fact oneInvList{

//ogni evento ha un'unica lista
	all e: Event | one il: InvitationList | e.invit = il

//ogni list appartiene a un solo evento
	all i: InvitationList | one e: Event | i in e.invit

//per ogni evento nella degli invitati esiste solo e uno e solo owner dell evento
	all e: Event | e.eo in e.invit.guest

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//le previsioni meteo sono inviate solo per gli eventi outdoor
assert forecastOnlyOutdoor{

	no e: Event| one f: ForecastWeather | e.etype = INDOOR and f.fcast in e

}

// check forecastOnlyOutdoor

//non esistono piu invitation list per un singolo evento
assert not2invitList {

	no disj il, il': InvitationList| one e: Event | e.invit = il and e.invit = il'

}

//check not2invitList

//non esiste nessun utente che può vedere calendari private se non è il proprietario
assert visibilityOtherCal{

	no c: Calendar| all u: User|  c in u.viewCal and c.visibility = PRIVATE and u != c.owner

}
	
//check visibilityOtherCal


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*pred show {
 	#User = 1
}
run show for 5

pred show {
 	#User = 3
	#Notification = 5
	#Event = 2
}
run show for 6*/

pred show{}
run show for 3
