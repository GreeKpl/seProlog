:- dynamic
    prawda/1,
    falsz/1,
    temperatura/1,
    nie_ma_choroby/1,
    choroba/1,
    lek/1.

/* 
19 leków, 19 chorób, 38 symptomów, 65 reguł
*/

lek(gripex).
lek(aspiryna).
lek(antybiotyk).
lek(chinina).
lek('lek przeciwzapalny').
lek('oklad na noge').
lek('oklad z lodu').
lek('unieruchomienie').
lek(stoperan).
lek('duzo plynow').
lek(apap).
lek(acviscin).
lek(chloramfenikol).
lek('antybiotyk, nawadnianie').
lek('lek przeciwhistaminowy, izoacja').
lek('wypoczynek, izolacja').
lek('lek przeciwgoraczkowy, izolacja').
lek('sok malinowy, wypoczynek').
lek('pobyt w szpitalu, penicylina').

lek_na_chorobe(gripex, grypa).
lek_na_chorobe(aspiryna, przeziebienie).
lek_na_chorobe(antybiotyk, odra).
lek_na_chorobe(chinina, malaria).
lek_na_chorobe('lek przeciwzapalny', 'zwyrodnienie stawow').
lek_na_chorobe('oklad na noge', 'zwichniecie stawu kolanowego').
lek_na_chorobe('oklad z lodu', 'brak dloni').
lek_na_chorobe('elastyczne unieruchomienie', 'skrecenie kostki').
lek_na_chorobe(stoperan, zatrucie).
lek_na_chorobe('duzo plynow', 'grypa zoladkowa').
lek_na_chorobe(apap, migrena).
lek_na_chorobe(acviscin, 'zapalenie pluc').
lek_na_chorobe(chloramfenikol, dzuma).
lek_na_chorobe('antybiotyk, nawadnianie', cholera).
lek_na_chorobe('lek przeciwhistaminowy, izolacja', 'ospa wietrzna').
lek_na_chorobe('wypoczynek, izolacja', 'świnka').
lek_na_chorobe('lek przeciwgoraczkowy, izolacja', 'różyczka').
lek_na_chorobe('sok malinowy, wypoczynek', 'zapalenie oskrzeli').
lek_na_chorobe('pobyt w szpitalu, penicylina', 'tężec').


choroba('skrecenie kostki') :-  jest_objaw('uraz mechaniczny'),
                                  jest_objaw('bol promieniujacy stopy').

choroba('zwyrodnienie stawow') :- jest_objaw('uraz mechaniczny'),
                                  jest_objaw('bol kolana'),
                                  jest_objaw('bol lokcia').

choroba('zwichniecie stawu kolanowego') :- jest_objaw('uraz mechaniczny'),
                                  jest_objaw('bol nogi').

choroba('grypa') :- objaw_temperaturowy('wysoka temperatura'),
					jest_objaw('bol glowy'),
					jest_objaw('bol stawow'),
					jest_objaw('suchy kaszel').

choroba('malaria') :- jest_objaw('drgawki'),
                                  jest_objaw('wymioty'),
                                  jest_objaw('dreszcze').

choroba('zatrucie') :- jest_objaw('wymioty'),
						objaw_temperaturowy('podwyzszona temperatura'),
						jest_objaw('biegunka'),
						jest_objaw('bol brzucha'),
						jest_objaw('bol glowy').

choroba('grypa zoladkowa') :- jest_objaw('wymioty'),
								jest_objaw('biegunka'),
								objaw_temperaturowy('wysoka temperatura'),
								jest_objaw('bol brzucha').

choroba('migrena') :- jest_objaw('bol glowy'),
						jest_objaw('nadwrazliwosc na swiatlo'),
						jest_objaw('nudnosci').

choroba('zapalenie pluc') :- jest_objaw('bol w klatce piersiowej'),
								jest_objaw('swiszczacy oddech'),
								jest_objaw('dreszcze'),
								jest_objaw('goraczka').

choroba('dzuma') :- objaw_temperaturowy('wysoka temperatura'),
						jest_objaw('dreszcze'),
						jest_objaw('silne poty'),
						jest_objaw('powiekszenie wezlow chlonnych').

choroba('cholera') :- \+jest_objaw('bol brzucha'),
						jest_objaw('biegunka'),
						jest_objaw('wymioty'),
						objaw_temperaturowy('brak temperatury').
						
choroba('ospa wietrzna') :- jest_objaw('wysypka'),
							objaw_temperaturowy('wysoka temperatura'),
							jest_objaw('kontakt z zarażonym ospą'),
							jest_objaw('swedzenie'),
							jest_objaw('katar').
							
choroba('świnka') :- jest_objaw('obrzęk slinianek przyusznych'),
					jest_objaw('bol slinianek przyusznych'),
					jest_objaw('kontakt z zarażonym świnką'),
					jest_objaw('brak apetytu'),
					jest_objaw('bol glowy'),
					objaw_temperaturowy('podwyzszona temperatura').
					
choroba('rożyczka') :- jest_objaw('wysypka'),
						\+jest_objaw('swedzenie'),
						objaw_temperaturowy('podwyzszona temperatura'),
						jest_objaw('powiekszone wezly chlonne').
						
choroba('zapalenie oskrzeli') :- objaw_temperaturowy('podwyzszona temperatura'),
								jest_objaw('suchy kaszel'),
								jest_objaw('mokry kaszel'),
								jest_objaw('bol glowy'),
								jest_objaw('zle samopoczucie').
								
choroba('tężec') :- jest_objaw('zabrudzone skaleczenie'),
					jest_objaw('przymusowy usmiech'),
					jest_objaw('bol glowy'),
					jest_objaw('dretwienie i mrowienie w okolicach skaleczenia'),
					jest_objaw('uczucie napiecia miesni'),
					objaw_temperaturowy('brak temperatury'),
					jest_objaw('drgawki').
										
							


wyswietl_pytanie(X) :- jpl_call('agh.se.Main', printQuestion, [X], _).
wyswietl_propozycje(L, C) :- jpl_call('agh.se.Main', printSolution, [C, L], _).
wyswietl_brak_propozycji :- jpl_call('agh.se.Main', informAboutNoSolution, [], _).

odbierz_odpowiedz(X) :- jpl_call('agh.se.Main', waitForAnswer, [], _), jpl_call('agh.se.Main', getAnswer, [], X).


start :- propozycja(L), lek(L).

propozycja(L) :- dobry_na_nasza_chorobe(L, C), wyswietl_propozycje(L, C), wyczysc_wiedze, !.
propozycja(_) :- wyswietl_brak_propozycji, wyczysc_wiedze.

dobry_na_nasza_chorobe(L, C) :- choroba(C), lek_na_chorobe(L, C).

objaw_temperaturowy('wysoka temperatura') :- temperatura(T), T >=  39.5.
objaw_temperaturowy('podwyzszona temperatura') :- temperatura(T), T >= 37.5, T < 39.5.
objaw_temperaturowy('brak temperatury') :- temperatura(T), T >= 36.0, T < 37.5.

jest_objaw(X) :- prawda(X).
jest_objaw(X) :- nieokreslony(X), czy(X), odbierz_odpowiedz(Odp), write(Odp),
	(Odp = 't' -> assertz(prawda(X));
		assertz(falsz(X)), fail).

nieokreslony(X) :- \+falsz(X), \+prawda(X).

czy(X) :- wyswietl_pytanie(X).

wyczysc_wiedze :- retractall(prawda(_)), retractall(falsz(_)).
