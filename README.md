![Zephyr logo](/assets/Zephyr%20long.png)

<hr>

# Zephyr

Zephyr is the sixth-generation multipurpose discord bot. This bot functions

The sixth generation multipurpose discord bot

This bot is intended to be a replacement for [Cutlery Bot](https://github.com/BSpoones/Cutlery-Bot)

<details open>
<summary>Modules</summary>

Below is a list of all modules in Zephyr, along with tutorials and other information.

Some commands may have the following format:
- 📌 Required argument
- 🟧 Optional argument

Contents:
 - [Archive](#archive)
 - [AutoPurge](#autopurge)
 - 
<details> <summary>Archive</summary>

## Archive

The archive module allows for message back-ups of a channel(s) within a discord server. This is closely tied to the
logging
module and allows for tracking to be enabled on messages. This includes but is not limited to: message edits, message
deletions,
message reactions, and much more!


| Type    | Command                     | Description                                 |
|---------|-----------------------------|---------------------------------------------|
| `SLASH` | `archive channel <channel>` | Archive a single text-based channel         | 
| `SLASH` | `archive all`               | Archive all text-based channels in a server | 

> Text based channels are all channels where messages can be sent (including voice chat text channels, forums, stages,
> etc.)

> **NOTE:** If you intend to use this bot on a public server, you **MUST** include a statement in your privacy policy
> mentioning that user's data is stored. Failing to include a privacy policy is a violation of the terms of use for
> Zephyr and may result in your discord server being blacklisted!

---
</details>

<details> <summary>AutoPurge</summary>

## AutoPurge

> **NOTE:** This module is only available for servers!

The AutoPurge module is used to automatically delete messages older than a chosen timeframe. This can help declutter
channels such as bot command channels. There is an option for pinned messages to be spared.

Commands:
- [Setup](#autopurge-setup)
- [Status](#autopurge-status)
- [Edit](#autopurge-edit)
- [Toggle](#autopurge-toggle)
- [Remove](#autopurge-remove)
- [Export](#autopurge-export)
- [Import](#autopurge-import)


#### AutoPurge Setup

AutoPurge can be applied to multiple channels at the same time, meaning you have to create an AutoPurge instance for each
channel you want to use it in. To set up AutoPurge in a channel, use the AutoPurge setup command:

```
/autopurge setup <cutoff> [channel] [ignore_pinned]
```

// TODO -> IMAGE

- 📌 Cutoff: How long should a message stay on the channel for? TODO LINK
- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel
- 🟧 Ignore_Pinned: Should AutoPurge ignore pinned messages? This defaults to true

---

#### AutoPurge Status

To retrieve the status of an AutoPurge instance, use the AutoPurge status command:

```
/autopurge status [channel]
```

- 🟧 Channel: Which channel should AutoPurge be setup in? This defaults to the current channel

TODO >> Show image of status

---

#### AutoPurge Edit

To edit the AutoPurge instance, use the AutoPurge edit command:

```
/autopurge edit {cutoff} {ignore_pinned}
```

- 🟧 Cutoff: How long should a message stay on the channel for? TODO LINK
- 🟧 Ignore_Pinned: Should AutoPurge ignore pinned messages? This defaults to true

---

#### AutoPurge Toggle

If AutoPurge needs to be disabled for a while, or re-enabled after disabling, use the AutoPurge toggle command

```
/autopurge toggle [channel]
```

- 🟧 Channel: Which channel should AutoPurge be toggled in? This defaults to the current channel

---

#### AutoPurge Remove

If AutoPurge is no longer required on a server, use AutoPurge remove to remove all AutoPurge capabilities.

```
/autopurge remove [channel]
```

- 🟧 Channel: Which channel should AutoPurge be removed in? This defaults to the current channel

#### AutoPurge Export

For ease of use, AutoPurge can be added to a channel via a JSON string, current AutoPurge instances can be exported and used
elsewhere.

```
/autopurge export [channel]
```

- 🟧 Channel: Which channel should have its AutoPurge settings exported? This defaults to the current channel

#### AutoPurge Import

This allows for AutoPurge settings to be imported from a JSON string.

```
/autopurge import <json> [channel]
```

- 📌 Json: The JSON string used for the import
- 🟧 Channel: Which channel should this apply to? This defaults to the current channel


</details>

## BanAll

## Logging

## Purge
</details>
