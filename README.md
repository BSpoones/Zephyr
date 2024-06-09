![Zephyr logo](/assets/Zephyr%20long.png)

<hr>

# Zephyr

Zephyr is the sixth-generation multipurpose discord bot. This bot functions

The sixth generation multipurpose discord bot

This bot is intended to be a replacement for [Cutlery Bot](https://github.com/BSpoones/Cutlery-Bot)

# Contents

- [Commands](#commands)
    - [Moderation](#moderation)
        - [Archive](#archive)
        - [AutoPurge](#autopurge)
            - [AutoPurge Setup](#autopurge-setup)
            - [AutoPurge Status](#autopurge-status)
            - [AutoPurge Edit](#autopurge-edit)
            - [AutoPurge Toggle](#autopurge-toggle)
            - [AutoPurge Remove](#autopurge-remove)
    - [BanAll](#banall)
    - [Logging](#logging)
    - [Purge](#purge)

# Commands

## Moderation

### Archive

Archive commands allow for back-ups of a channel or server. All messages are saved to a database to track message edits,
deletions, and other logging-based purposes. Due to Discord API limitations, it is not always possible to view the
content
of deleted messages.

**Usage**

To archive a single channel, use the command below. This only stores messages from the given channel.

```
/archive channel <channel>
```

---

To archive all channels, use the command below. This stores all messages from all text channels (including VC text
channels
, forums, stages, etc.).

```
/archive all
```

> **NOTE:** If you intend to use this bot on a public server, you **MUST** include a statement in your privacy policy
> mentioning that user's data is stored. Failing to include a privacy policy is a violation of the terms of use for
> Zephyr and may result in your discord server being blacklisted!

---

### AutoPurge

<i>**Note - This can only be used in servers**</i>

#### AutoPurge Setup

AutoPurge allows for messages older than a chosen timeframe to be deleted. This can help declutter channels such as
bot command channels. Pinned messages can optionally be kept.

**Usage**

To set up AutoPurge in a channel, use the AutoPurge setup command:

```
/autopurge setup <cutoff> [channel] [ignore_pinned]
```

- 📌 Cutoff: How long should a message stay on the channel for? TODO LINK
- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel
- 🟧 Ignore_Pinned: Should AutoPurge ignore pinned messages? This defaults to true

---

#### AutoPurge Status

To retrieve the status of an AutoPurge instance, use the AutoPurge status command:

```
/autopurge status [chanell]
```

- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel

TODO >> Show image of status

---

#### AutoPurge Edit

To edit the AutoPurge instance, use the AutoPurge edit command:

```
/autopurge edit [cutoff] [ignore_pinned]
```

- 🟧 Cutoff: How long should a message stay on the channel for? TODO LINK
- 🟧 Ignore_Pinned: Should AutoPurge ignore pinned messages? This defaults to true

---

#### AutoPurge Toggle

If AutoPurge needs to be disabled for a while, or re-enabled after disabling, use the AutoPurge toggle command

```
/autopurge toggle [channel]
```

- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel

---

#### AutoPurge Remove

If AutoPurge is no longer required on a server, use AutoPurge remove to remove all AutoPurge capabilities.

```
/autopurge remove [channel]
```

- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel

### BanAll

### Logging

### Purge
