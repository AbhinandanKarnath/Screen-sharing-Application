# Local Network Screen Sharing

---

## The Problem

In college, when a lecturer wanted to show something on their screen, the entire class would physically crowd around their PC — even though every student had their own machine connected to the same network. There was no screen sharing tool available, and no one wanted to install heavyweight software just for a classroom demo.

So I built one.

---

## The Solution

It works entirely over a **local area network (LAN)** — no internet required, no STUN/TURN servers, no accounts, no setup overhead.

- 📡 One person shares their screen (the teacher/presenter)
- 👀 Everyone else on the network receives it in real time
- 💬 A built-in chat panel lets the class communicate during the session
- 🔇 No audio, no camera — because in a physical classroom, you don't need them

---

## Features

| Feature | Details |
|---|---|
| **Screen Broadcast** | Captures and streams the presenter's screen to all receivers |
| **Multicast Delivery** | Uses UDP multicast — one sender, many receivers, no relay server |
| **Built-in Chat** | Text chat alongside the stream for questions and discussion |
| **Configurable Network** | Set your multicast address, port, and network interface via the UI |
| **LAN Only** | Designed for trusted local networks — simple and fast |
| **One Broadcaster at a Time** | Only one person shares at a time; others are viewers |

---

## How It Works

### Screen Capture & Broadcast

The presenter's side uses Java's `Robot` class to take periodic screenshots of the screen. Each frame is compressed as a JPEG and broken into UDP packets:

```java
BufferedImage image = robot.createScreenCapture(rectangle);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
ImageIO.write(image, "jpg", bos);
byte[] bytes = bos.toByteArray();
```

These packets are sent over a **UDP multicast socket** to a multicast group address — meaning every machine that has joined the group receives the data simultaneously without the sender needing to know who's listening.

### Receiving & Reassembly

On the receiver side, packets are collected and stitched back together into the full image:

```java
while (!(socket.isClosed())) {
    byte[] bytes = new byte[1024];
    packet = new DatagramPacket(bytes, bytes.length, group, port);
    socket.receive(packet);
    // ... accumulate packets until a frame is complete
    if (packetLength < 1024 || socket.isClosed()) {
        break; // last packet of frame
    }
}
```

A frame is considered complete when a packet smaller than the buffer size (1024 bytes) arrives — indicating the end of the stream for that image.

### Network Configuration

Network settings (multicast addresses, ports, interface names) are managed through a `NetworkSettings` class and configurable via the JavaFX UI:

```java
NetworkSettings settings = new NetworkSettings();
int port = settings.getScreenPortNumber();
String inetAddressName = settings.getScreenNetworkInetAddress();
socket = new MulticastSocket(port);
socket.joinGroup(new InetSocketAddress(group, port),
                 NetworkInterface.getByName(networkInterfaceName));
```

The UI lets you split and enter the multicast IP address in four separate fields (like `239.0.0.1`) and specify the network interface by name — making it easy to switch between network adapters.

---

## Tech Stack

- **Java** — Core logic, networking, screen capture
- **JavaFX** — UI framework (FXML-based controllers)
- **UDP Multicast** — Transport layer for screen data and chat
- **`java.awt.Robot`** — Screen capture
- **`javax.imageio.ImageIO`** — JPEG compression

---

## Requirements

- Java 11 or later (with JavaFX SDK if not bundled)
- All machines must be on the **same LAN**
- The network must support **UDP multicast** (most local routers/switches do)
- Firewall must allow the configured UDP port

---

## Configuration

On first launch, open the **Network Settings** panel and configure:

| Field | Description | Example |
|---|---|---|
| Screen Multicast Address | IP for the screen stream group | `239.0.0.1` |
| Screen Port | UDP port for screen data | `5000` |
| Screen Interface | Network interface name | `eth0` / `wlan0` |
| Chat Multicast Address | IP for the chat group | `239.0.0.2` |
| Chat Port | UDP port for chat | `5001` |
| Chat Interface | Network interface name | `eth0` / `wlan0` |

> **Tip:** Use addresses in the `239.x.x.x` range — these are reserved for local multicast and won't leak outside your LAN.

---

## Running the App

```bash
# Compile
javac --module-path /path/to/javafx-sdk/lib \
      --add-modules javafx.controls,javafx.fxml \
      -d out src/**/*.java

# Run
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp out com.project.Main
```

Or use your IDE (IntelliJ IDEA / Eclipse) with the JavaFX plugin configured.

---

## Architecture Overview

```
┌─────────────────────────────────┐
│         Presenter (Sender)      │
│  Robot → JPEG → UDP packets     │
│         Multicast Group         │
└────────────────┬────────────────┘
                 │ UDP Multicast
        ┌────────┴────────┐
        │                 │
┌───────▼──────┐   ┌──────▼───────┐
│  Receiver 1  │   │  Receiver 2  │  ...
│ Reassemble → │   │ Reassemble → │
│ Display image│   │ Display image│
└──────────────┘   └──────────────┘

Chat works the same way — multicast UDP on a separate group/port.
```

---

## Limitations & Design Decisions

- **Single broadcaster** — by design. This is a classroom tool, not a conference platform.
- **No authentication** — anyone on the LAN can join the multicast group. Intended for trusted networks.
- **No audio/video camera** — deliberately omitted. In a physical classroom, you have the teacher's voice. The screen is all that's needed.
- **No STUN/TURN servers** — works only on LAN. No internet routing is needed or supported.
- **UDP (no guaranteed delivery)** — occasional frame drops can occur under heavy network load. Acceptable for a live screen share context.

---

## Why I Built This

> *"In my college, there was no screen sharing option. So when the lecturer wanted to demo something, the entire class would crowd around their PC — even though everyone had their own machine on the same network. I thought: why not just let the teacher share their screen, with a chat window alongside it?"*

This project was built to solve a real, everyday problem with the simplest possible approach — no cloud services, no complicated setup, just Java and the network that was already there.

---

## License

This project is open for personal and educational use. Feel free to fork and adapt it for your own institution.
