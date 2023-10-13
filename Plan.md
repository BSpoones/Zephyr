```
 oooooooooooo                      oooo                             
d'""""""d888'                      `888                             
      .888P    .ooooo.  oo.ooooo.   888 .oo.   oooo    ooo oooo d8b 
     d888'    d88' `88b  888' `88b  888P"Y88b   `88.  .8'  `888""8P 
   .888P      888ooo888  888   888  888   888    `88..8'    888     
  d888'    .P 888    .o  888   888  888   888     `888'     888     
.8888888888P  `Y8bod8P'  888bod8P' o888o o888o     .8'     d888b    
                         888                   .o..P'               
 Made by BSpoones       o888o                  `Y8P'                
 Version <VERSION>                                                               
```

# Command list:

## Reminder commands:
 - Remind every
 - Remind in
 - Remind at
 - List Reminders
 - Delete Reminder
 
## Logging commands:
 - Logging setup
 - Logging shutdown
 - Logging toggle - Toggles logging on the channel itself / server-wide
 - Logging blacklist/whitelist
 - Logging add_event
 - Logging remove_event
 - Logging status
 - Logging import (JSON)
 - Logging export (JSON)

## Non-cog commands:
 - Calculate
 - Big
 - Coinflip
 - QRCode
 - Type
 - UnixTime
 - Userinfo
 - Serverinfo
 - Channelinfo
 - Banall
 - Purge

## Bot info commands:
 - Bot info
 - Bot ping
 - Bot set_status
 - Bot shutdown
 - Bot uptime

## Command commands:
 - Command logs
 - Command leaderboard

## AutoPurge commands:
 - AutoPurge setup
 - AutoPurge shutdown
 - AutoPurge edit
 - AutoPurge Toggle
 - AutoPurge status
 - AutoPurge import (JSON)
 - AutoPurge export (JSON)

## Archive commands:
 - Archive server
 - Archive channel

## Filter commands:
 - Filter setup
 - Filter shutdown
 - Filter add_rule
 - Filter remove_rule
 - Filter add_exception (user | role | channel)
 - Filter remove_exception
 - Filter import (JSON)
 - Filter export (JSON)

## RolePersistence commands:
 - RolePersitence setup
 - RolePersistence shutdown
 - RolePersistence blacklist (Role | User)
 - RolePersistence whitelist (Role | User)
 - RolePersistence status

## Ticket commands:
  (Eventually replaced by website)
 - Ticket setup
 - Ticket shutdown
 - Ticket list 

# Command description

The following are command descriptions, as well as argument information.

`[REQUIRED]` and `<OPTIONAL>` are shown as such.
`{ARG_1} || {ARG_2}` implies that at least one of the arguments must be provided

## Reminders
Send custom reminders to a user or role. 

Each reminder has a unique 4 character, BASE-36 ID, allowing for 1,679,616 reminders at once (36^4).

An asyncronous scheduler will be used in combination with a CronTrigger to send reminders that are both repeating and singular. When the bot runs & every day, Zephyr will add all applicable events to the scheduler (only adding reminders that will occur on that day for optimisations sake)

### Remind every
Reminds a user | role every x time. Using `1h1m1s` time format. The command args are as follows:
```java
/remind every ({TIMEFRAME} || {DATE} && {TIME}) [MESSAGE] <TARGET> ({OCCURENCES} || {END_DATE}) <PRIVATE> <TIMEZONE>
``` 
 - `TIMEFRAME` - Time format for non-datetime datetimes. E.g `1h1m1s` means 1 hour, 1 minute, 1 second in the future
 - `DATE` - YYYYMMDD | MMDD | DD | Weekday | "day" | "hour". Either a callender date, monthly date, a day of the month, a weekday, or every day.
 -- For a day reminder (every month on a specific day), the highest day will be selected if the original day cannot me bet. E.g: Reminders set for the 31st of the month may be set to the 30th if that month does not have 30 days, essentially being rounded down to ensure that they occur once a month. Users will be notified of this in their reminder setup message and their reminder message
 - `TIME` : HHMM | HHMMSS time format
 - `MESSAGE` : Reminder message
 - `TARGET` : User | Role
 - `OCCURENCES` : Absorbing `/remind per`, occurences allow for the reminder to be sent out an amount of times
 - `END_DATE` : YYYYMMDD | YYYYMMDDHHMMSS
 - `PRIVATE` : Sends reminder in DM if possible (error handling included) if True, sends in channel of command invoke if not (error handling for DM). Default true
 - `TIMEZONE` : Adjusts the reminder datetime (if given) to the user's timezone. If a user has set a timezone, this is done automatically. Defaults to UK time if null 

### Remind in
Reminds a user | role in x amount of time. Timeframes only are used
```java
/remind in [TIMEFRAME] [MESSAGE] <TARGET> <PRIVATE>
```
 - `TIMEFRAME`: non datetime based timeframe. `1h1m1s` = 1 hour, 1 minute, 1 second in the future
 - `MESSAGE` : Reminder message
 - `TARGET` : User | Role
 - `PRIVATE` : Sends reminder in DM if possible (error handling included) if True, sends in channel of command invoke if not (error handling for DM). Default true

### Remind at
Reminds a user | role at a specific datetime
```java
/remind at [DATETIME] [MESSAGE] <TARGET> <PRIVATE>
```
 - `DATETIME` : YYYYMMDDHHMM(SS) | HHMM(SS) datetime of reminder
 - `MESSAGE` : Reminder message
 - `TARGET` : User | Role
 - `PRIVATE` : Sends reminder in DM if possible (error handling included) if True, sends in channel of command invoke if not (error handling for DM). Default true

### List reminders
Lists all reminders for a user | role
```java
/list_reminders <SERVER_ONLY>
```
 - `SERVER_ONLY` - Show reminders for just that server instead of bot-wide? Default false, non-server reminders will return an ephemeral message

### Delete reminders
Deletes a reminder
```java
/delete_reminder [REMINDER_ID]
```
 - `REMINDER_ID` ID of reminder