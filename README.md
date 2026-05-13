# Summit — LAN-Based Classroom Screen Sharing

---

## The Problem

In college, when a lecturer wanted to demo something, the entire class would physically crowd around their PC — even though every student had their own machine connected to the same network. No lightweight screen sharing tool existed for the classroom LAN.

**Summit** is that tool.

---

## What It Does

A real-time screen broadcasting application built for classroom environments. The teacher shares their screen; every student receives it — simultaneously, over the local network, with a live chat panel alongside.

- **One presenter** captures and streams their screen
- **Unlimited viewers** receive it in real time via UDP multicast
- **Built-in chat** runs alongside the stream for questions
- **No audio, no camera** — in a physical classroom, you already have the teacher's voice
- **LAN only** — no internet, no accounts, no servers, no setup overhead

---

## Architecture — MVC Pattern

Summit strictly follows the **Model-View-Controller** pattern, reflected directly in the package structure:

| Layer | Package | Responsibility |
|---|---|---|
| **Model** | `com.project.model` | Business logic, networking, data |
| **View** | `com.project.view` + FXML files | UI rendering, audio feedback |
| **Controller** | `com.project.controller` | Event handling, scene navigation |

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **Java 18** | Core language |
| **JavaFX 20 (FXML)** | GUI framework |
| **JFoenix 9** | Material Design UI components |
| **UDP Multicast Sockets** | Screen & chat data transport |
| **`java.awt.Robot`** | Screen capture |
| **`javax.imageio.ImageIO`** | JPEG image compression |
| **Maven** | Build tool & dependency management |
| **JUnit 5** | Testing framework |

---

## Features

- **Screen Broadcasting** — Presenter captures and streams their screen in real time
- **Multicast Delivery** — One sender, unlimited receivers, no relay server needed
- **Live Chat** — Text chat over a separate multicast channel alongside the stream
- **Network Configuration UI** — Set multicast IPs, ports, and interfaces from within the app
- **Role-based Sign-in** — Users sign in with a name and designation (Teacher / Student)
- **Audio Feedback** — Sound effects on navigation and connection events

---

## How It Works

### UDP Multicast — Core Transport

Summit uses two separate multicast channels so screen data and chat packets never interfere with each other:

| Channel | Default Address | Default Port |
|---|---|---|
| Screen stream | `227.0.0.0` | `5000` |
| Chat messages | `226.0.0.0` | `1234` |

Receivers subscribe to a group by calling `MulticastSocket.joinGroup()`. The TTL is set to `1`, which keeps packets confined to the local network — they cannot escape the LAN.

### Screen Capture & Packet Fragmentation

```java
// 1. Capture the screen
BufferedImage image = robot.createScreenCapture(rectangle);

// 2. Compress to JPEG
ByteArrayOutputStream bos = new ByteArrayOutputStream();
ImageIO.write(image, "jpg", bos);
byte[] bytes = bos.toByteArray();

// 3. Send in 1024-byte UDP packets sequentially
```

On the receiver side, packets are accumulated and stitched back together into a full frame. The end of a frame is detected when a packet smaller than 1024 bytes arrives:

```java
while (!(socket.isClosed())) {
    byte[] bytes = new byte[1024];
    packet = new DatagramPacket(bytes, bytes.length, group, port);
    socket.receive(packet);

    // Accumulate into complete[]
    int packetLength = packet.getLength();
    byte[] newImageData = new byte[complete.length + packetLength];
    System.arraycopy(complete, 0, newImageData, 0, complete.length);
    System.arraycopy(imgData, 0, newImageData, complete.length, packetLength);
    complete = newImageData;

    if (packetLength < 1024 || socket.isClosed()) {
        break; // End of frame
    }
}
```

### Multithreading

Sending and receiving run on dedicated background threads so the UI never freezes. Chat uses a JavaFX `Task<Void>` with `Platform.runLater()` to safely push updates back to the UI thread.

### Network Configuration

All network settings are managed through a `NetworkSettings` class and editable in the UI. The multicast IP is split into four individual fields for easy entry:

```java
NetworkSettings settings = new NetworkSettings();
int port = settings.getScreenPortNumber();
String inetAddressName = settings.getScreenNetworkInetAddress();

socket = new MulticastSocket(port);
socket.joinGroup(
    new InetSocketAddress(group, port),
    NetworkInterface.getByName(networkInterfaceName)
);
socket.setTimeToLive(1);
```

---

## Class Breakdown

### Model Layer

| Class / Interface | Role |
|---|---|
| `BroadcastSender` | Screen capture loop, JPEG compression, UDP multicast sending |
| `BroadcastReceiver` | Joins multicast group, reassembles fragmented packets into frames |
| `Message` | Chat send/receive over a separate multicast socket |
| `NetworkSettings` | Stores and manages all network configuration (implements `Network`) |
| `User` | Holds user name and designation (implements `UserInterface`) |
| `Network` *(interface)* | Contract for all network setting getters/setters |
| `UserInterface` *(interface)* | Contract for user data getters/setters |

### Controller Layer

| Class | Role |
|---|---|
| `Main` | JavaFX `Application` entry point, loads the first FXML scene |
| `ApplicationController` | Controls Send/Receive toggle, manages stream lifecycle |
| `ChatBoxController` | Manages chat UI, sends/receives messages in real time |
| `NetworkController` | Validates and saves network configuration settings |
| `SceneController` | Handles all scene/screen navigation |
| `grp` | Auxiliary controller for home screen labels |

### View Layer

| Component | Role |
|---|---|
| `Sounds` | Plays audio feedback via JavaFX `MediaPlayer` |
| `graphics.java` | Reserved for future graphics utilities |
| 9 FXML files | Declarative UI layouts for each screen |

---

## Application Flow

```
Launch → Sign-In Screen → Home Screen
                              │
                 ┌────────────┴────────────┐
                 │                         │
          [Teacher/Broadcaster]      [Student/Receiver]
          Sender View                Receiver View
          Start Hosting →            Join Network →
          Stream Loop (Thread)       Display Loop (Thread)
                 │                         │
                 └────────┬────────────────┘
                          │
                   Chat Panel (both sides)
                   Multicast Chat Loop
```

---

## OOP Design — Interfaces

Summit uses interface-based programming as a core OOP principle:

- **`Network`** — enforces a contract for network settings (getters + setters for both screen and chat channels)
- **`UserInterface`** — enforces user data structure (name + designation)

Both `NetworkSettings` and `User` implement these interfaces, keeping the model layer decoupled and testable.

---

## Design Decisions

| Decision | Reason |
|---|---|
| **UDP over TCP** | No connection overhead; occasional frame drops are acceptable in a live stream |
| **Multicast over Unicast** | One sender reaches all receivers without needing to know who they are |
| **JPEG compression** | Dramatically reduces packet volume compared to raw bitmap data |
| **LAN-only** | No STUN/TURN servers needed; simpler, faster, no internet dependency |
| **Single broadcaster** | Classroom use case — teacher presents, students receive |
| **No authentication** | Trusted local network assumption; minimises setup friction |
| **Separate chat channel** | Prevents chat packets from colliding with screen data |

---

## Known Limitations

- **No audio or camera** — by design; in a physical classroom the teacher's voice is already present
- **No encryption** — packets are plaintext on the LAN; intended for trusted networks only
- **No guaranteed frame delivery** — a dropped UDP packet means a skipped frame
- **No reconnection logic** — if the network drops mid-session, the user must rejoin manually
- **Fixed 1024-byte packet size** — not adaptive to varying network conditions

---

## Requirements

- Java 18 or later
- JavaFX 20 SDK
- Maven 3.x
- All machines on the **same LAN**
- Network must support **UDP multicast** (most local routers/switches do by default)
- Firewall must allow the configured UDP ports

---

## Build & Run

```bash
# Build
mvn clean package

# Run
mvn javafx:run
```

Or open in IntelliJ IDEA / Eclipse with the JavaFX plugin configured and run `Main.java`.

---

## License

Open for personal and educational use. Feel free to fork and adapt it for your own institution.
