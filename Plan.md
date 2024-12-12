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
 
## Logging

Logging tracks events in a server. 

Current strengths:
 - Works for all events nicely
 - By far the most used feature of CB/KBot

Current drawbacks
 - Out of date, modern events not tracked
 - Relies heavily on database integrity. Threads don't work
 - Not intuitive
 - Can't manually select events

Needs:
 - Make it work with threads
 - Optimised data structure, possibly still SQL
 - Make it follow TOS, hand blame to server owners?

Wants:
 - Easier way to setup
 - Easier way to add individual events. Current block is discord's 25 item limit. Possibly use suggestion provider?
 - Website dashboard for this

Actual commands:
 - Logging setup
 - Logging remove
 - Logging event add|remove|list
 - Logging toggle
 - Logging ignore user|rank|channel|category
   - Have them as a command option via enum
 - Logging allow user|rank|channel|category
 - Logging status
 - Logging import (JSON)
 - Logging export (JSON)

## Non-module commands:
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

This should be message commands, and should only apply on config approved servers AND/OR approved users

 - Bot info
 - Bot ping
 - Bot uptime

Bot shutdown and status to be moved to config based

## Command commands:
 - Command leaderboard

Command logs is stupid to be publicly available

## AutoPurge commands:
 - AutoPurge setup
 - AutoPurge shutdown
 - AutoPurge edit
 - AutoPurge Toggle
 - AutoPurge status
 - AutoPurge import (JSON)
 - AutoPurge export (JSON)

Run task every minute, and find a more efficient way to do this than just force deleting all messages.
Possible create a cache, detect a message coming in, if it's in AP channel, mark for removal?


## Archive commands:
 - Archive server
 - Archive channel

This needs to be SQL based, possibly blob storage for files? Can this data be anonimised somehow?
This is really handy for message delete and edit tracking, so that's a need. Make it follow TOS!!!


## Filter commands:
 - Filter setup
 - Filter remove
 - Filter rule add|remove|list
- Logging ignore user|rank|channel|category
    - Have them as a command option via enum
- Logging allow user|rank|channel|category
 - Filter import (JSON)
 - Filter export (JSON)

## RolePersistence commands:
 - RolePersitence setup
 - RolePersistence remove
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

# Reminders
Send custom reminders to a user or role. 

Each reminder has a unique 4 character, BASE-36 ID, allowing for 1,679,616 reminders at once (36^4).

An asyncronous scheduler will be used in combination with a CronTrigger to send reminders that are both repeating and singular. When the bot runs & every day, Zephyr will add all applicable events to the scheduler (only adding reminders that will occur on that day for optimisations sake)

When a private reminder is sent, the reminder embed is sent to the user via a DM, error handling will be in place to check if the message could be sent. If not, the reminder will be sent in the channel the command was invoked in, with a reminder to update their safety settings to allow a DM

To prevent spam, all repeating reminders <24 hours in public channels will be auto set to private (unless an administrator sets it, then the limit is <5m).

When deleting private reminders, only the creator OR the target of the reminder can delete the reminder. On public reminders, only the creator, target or an administrator can delete the reminder. Administrators of a server are able to view all public reminders.

If a user has a public reminder on a server they have left, that reminder will be deleted, with a confirmation sent to the user that the reminder was deleted because they left the server.

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