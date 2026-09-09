<div align="center">

# 🌊 AethraSea

### A Folia-based server core for people who want **both** — old plugins and modern performance

**โปรเจกต์ส่วนตัวที่เปิดให้ทุกคนได้ลองใช้ · ฟรี 100% · fork โดยตรงจาก Folia 26.2**

---

`Minecraft 26.2` · `Java 21+` · `Folia-based` · `Hybrid Engine`

</div>

---

## 📖 สารบัญ

- [ทำไมต้อง AethraSea?](#ทำไมต้อง-aethrasea)
- [✨ ความสามารถหลัก](#-ความสามารถหลัก)
- [🎮 คำสั่งในเกม](#-คำสั่งในเกม)
- [🗺️ sea.yml คืออะไร](#️-sea.yml-คืออะไร)
- [🤖 เรื่อง AI ตรงไปตรงมา](#-เรื่อง-ai-ตรงไปตรงมา)
- [📦 วิธีติดตั้ง](#-วิธีติดตั้ง)
- [🛠️ Build จาก Source](#️-build-จาก-source)
- [📂 โครงสร้างแพตช์](#-โครงสร้างแพตช์)
- [🏷️ เวอร์ชัน & Release](#️-เวอร์ชัน--release)
- [💬 ข้อควรรู้ก่อนอัปเกรด](#-ข้อควรรู้ก่อนอัปเกรด)
- [⚖️ License](#️-license)

---

## ทำไมต้อง AethraSea?

AethraSea เริ่มต้นจากโปรเจกต์ **ส่วนตัว** — ผมต้องการ server engine ที่แรงพอจะรันเซิร์ฟเวอร์
ของตัวเองแบบลื่น ๆ โดยไม่ต้องทิ้งปลั๊กอินตัวเก่าที่ใช้มานาน แต่พอทำไปทำมา มันก็กลายเป็นอะไรสักอย่าง
ที่อยากแชร์ให้คนอื่นได้ลองใช้ด้วย

จุดเด่นที่สุดของ AethraSea คือ **Hybrid Engine**:

> เอา **region threading ของ Folia**
> มาผสมกับความสามารถในการโหลด **ปลั๊กอินแบบเดิม** (Bukkit / Spigot / Paper API)
> ที่ปกติจะรันบน Folia ตรง ๆ ไม่ได้ — เราปรับให้มันรันได้จริง

ผลลัพธ์คือ **ได้ทั้งความเร็ว แถมปลั๊กอินเก่า
ยังใช้ต่อได้ [บางตัว]** ไม่ต้องเขียนปลั๊กอินใหม่จากศูนย์

---

## ✨ ความสามารถหลัก

| ฟีเจอร์ | รายละเอียด |
| --- | --- |
| 🧬 **Hybrid Engine** | รันปลั๊กอิน Bukkit/Spigot/Paper บน Folia ได้จริง ⚠️ ระบบที่ไปยุ่งกับโครงสร้างโลก (world) โดยตรงอาจมีปัญหาได้ เพราะ Folia แยก thread ต่อ region |
| ⚡ **Parallel Chunk Loading** | เปิด/ปิดโหมดขนานของการโหลด+เจน chunk ผ่าน `sea.yml` — ตั้งจำนวน worker / I/O thread ได้ เหมาะกับทั้งเครื่องแรงและเครื่องกาก |
| 🌊 **sea.yml** | คอนฟิกทุกอย่างรวมอยู่ในไฟล์เดียว มี **comment อธิบาย** กำกับทุกค่า สร้างอัตโนมัติตอนเปิดครั้งแรก |
| 📊 **Boss Bar HUD** | `/tpsbar` และ `/rambar` แสดง TPS / MSPT / RAM เป็น **boss bar** อัปเดตทุก 1 วินาที เปิดปิดได้รายคน |
| 🖥️ **/seagui** | Dashboard สถานะเครื่องในเกม — async chunk I/O, pathfinding, network, memory และอื่น ๆ พร้อมปุ่มรีเฟรช |
| 🛡️ **Packet Flood Limiter** | กันผู้เล่นที่ส่งแพ็กเก็ตเกินกำหนด (packets/second) อัตโนมัติ — ลดการ crash จาก bot / lag machine |
| ⚡ **Async Chunk I/O** | โหลด/บันทึก chunk แบบ async หลังบ้าน ไม่เบียด main tick |
| 🧠 **Async Pathfinding** | โยนงานหาเส้นทาง (A*) ของม็อบไปให้ worker pool แยก ตั้งจำนวน thread ได้ |
| 🔀 **Smart Region Merging** | ปรับ hysteresis ของ Folia region merge เพื่อลด context switch บน chunk border |
| 🔔 **Monitor Warning** | ถ้า TPS ต่ำ หรือ RAM ใกล้เต็ม จะแจ้งเตือน op + ลง log |
| 📊 **MSPT จริง** | ค่า MSPT คำนวณจาก `timePerTickData` ตรงกับตัวเลขของ Folia `/tps` — ไม่ใช่แค่ 1000/TPS |

> ⚠️ ใน `sea.yml` ยังมี option อีกกลุ่มที่ **แสดงสถานะได้ แต่ยังไม่ได้เปิดใช้งานจริง** เช่น
> stasis chamber fix, cross-region fluid, anti-rubberband, book/sign sanitizer — เพราะมันเกี่ยวกับ
> gameplay/packet โดยตรงต้องค่อย ๆ คัดกรองกับเซิร์ฟเวอร์จริงก่อน กันพัง ผมเปิดปิดได้ในคอนฟิก
> แต่ขอ flag ว่า **ยังไม่ยืนยันเสถียร 100%**

---

## 🎮 คำสั่งในเกม

| คำสั่ง | ความหมาย |
| --- | --- |
| `/tpsbar [on\|off]` | เปิด/ปิด boss bar โชว์ TPS, MSPT และ worst region ต่อเนื่อง |
| `/rambar [on\|off]` | เปิด/ปิด boss bar โชว์การใช้ RAM แบบเรียลไทม์ |
| `/seagui` | เปิด dashboard สถานะระบบในเกม |
| `/seagui reload` | รีโหลด `sea.yml` (เรียกใช้พร้อมกันกับตั้งค่า chunk system ใหม่) |

---

## 🗺️ sea.yml คืออะไร

ไฟล์คอนฟิกกลางของ AethraSea — สร้างอัตโนมัติที่โฟลเดอร์เซิร์ฟเวอร์ตอนเปิดครั้งแรก
พร้อม comment อธิบายทุกค่า (ตัวอย่างโครงสร้างจริง):

```yaml
brand:
  f3-brand-name: 'AethraSea'     # ชื่อที่แสดงบน F3
  server-name: 'AethraSea'       # ชื่อเซิร์ฟเวอร์

performance:
  smart-region-merging: true     # ปรับ region merge hysteresis
  region-merge-hysteresis-ticks: 20
  async-chunk-io: true           # Async chunk load/save
  async-pathfinding: true        # Mob pathfinding offload
  pathfinding-threads: 4         # จำนวน thread สำหรับ pathfinding
  chunk-system:                  # Parallel chunk load/generate
    parallel-chunk-loading: true  # true = ขนาน / false = serial (1 thread)
    chunk-loading-parallelism: 0  # 0 = auto (cores/2), ตั้งเองได้
    chunk-io-threads: 0           # 0 = auto, ตั้งเองได้
  monitor-warning:
    enabled: true                # แจ้งเตือน op อัตโนมัติ
    tps-threshold: 18.0          # เตือนถ้า median TPS ต่ำกว่านี้
    ram-percent-threshold: 85    # เตือนถ้า RAM ใช้เกิน %

gameplay:
  stasis-chamber-fix: true       # ยังอยู่ในระยะทดสอบ
  smooth-cross-region-fluids: true
  cross-region-vehicle-rubberband-fix: true
  cross-region-villager-poi-sync: true

network:
  packet-flood-limiter: true     # กัน packet spam
  max-packets-per-second: 500    # ขีดจำกัดก่อนโดนเตะ
  region-packet-rate-limit: true
  async-packet-events: true

security:
  graceful-region-recovery: true # กู้คืน region ที่ thread พัง
  book-sign-payload-validator: true
  max-sign-text-length: 384
  max-book-pages: 100
  max-book-page-length: 1024

gui:
  enabled: true                  # เปิด /seagui
```

---

## 🤖 เรื่อง AI ตรงไปตรงมา

ผมขอพูดตรง ๆ เลย:

> **ส่วนหนึ่งของโค้ดในโปรเจกต์นี้ถูกเขียน/ช่วยเขียนโดยเครื่องมือ AI**
> แต่ผม **ตรวจทานโค้ดทุกบรรทัด** ก่อนที่จะ merge ทุกครั้ง — ทั้ง logic, thread safety,
> ความเข้ากันได้กับ Folia และผลข้างเคียงกับปลั๊กอินเก่า แล้วยังเทสต์จริงบนเซิร์ฟเวอร์
> ก่อนปล่อย build

ผมไม่ได้เอาโค้ด AI มาใส่โดยไม่ดู ถ้าเจอจุดบกพร่องจากโค้ดที่ AI เขียน ผมแก้และบันทึกไว้
**เอาโค้ดนี้ไปใช้ได้ แต่ถ้าจะเอาไปต่อยอด แนะนำให้อ่านผ่านก่อนนะครับ** 🙂

---

## 📦 วิธีติดตั้ง

1. **สำรองไฟล์** ที่สำคัญของเซิร์ฟเวอร์เดิมก่อน (world, plugins, config) ⚠️
2. ดาวน์โหลด build ล่าสุดจาก **Releases** (ไฟล์ `AethraSea-26.2-SNAPSHOT.jar`) แล้วเปลี่ยนชื่อเป็น `server.jar`
3. วางในโฟลเดอร์เซิร์ฟเวอร์ แล้วเริ่มเครื่อง:

```bash
java -Xms2G -Xmx4G --nogui -jar server.jar
```

หรือถ้าใช้ Windows:

```powershell
java -Xms2G -Xmx4G -jar server.jar --nogui
```

4. เปิดครั้งแรกต้องรับข้อตกลง EULA (`eula.txt` → `eula=true`)
5. รอขึ้นแล้ว — `sea.yml`, `bukkit.yml` และไฟล์คอนฟิกอื่นสร้างอัตโนมัติ
6. วางปลั๊กอินลงใน `plugins/` เหมือนเดิม แล้ว restart

---

## 🛠️ Build จาก Source

เตรียม: **JDK 21+** (ผม build ด้วย JDK 26), **Git**

```powershell
git clone https://github.com/seazexyz/AethraSea.git AethraSea
cd AethraSea
git checkout ver/26.2

# ใช้แพตช์ทั้งหมดลง source
.\gradlew.bat applyAllPatches --no-configuration-cache

# ลบ jar เก่า (กัน cache เก่า)
Remove-Item folia-server\build\libs\*.jar

# สร้าง paperclip jar
.\gradlew.bat createPaperclipJar --no-configuration-cache
```

เสร็จแล้วได้ไฟล์ที่ `folia-server\build\libs\folia-paperclip-26.2-SNAPSHOT.jar`

> 💡 เทคนิค: ห้ามรัน `rebuildPaperServerPatches` และ `applyAllPatches` ในคำสั่งเดียวกัน
> Gradle 9 ไม่ให้ — ต้องรันแยกกันเสมอ

---

## 📂 โครงสร้างแพตช์

โปรเจกต์นี้เป็น Folia fork แบบ "patches-based" ตามสไตล์ Paper/Folia ดั้งเดิม:

| ที่ | คืออะไร |
| --- | --- |
| `folia-server/minecraft-patches/features/` | แพตช์ระดับ Minecraft network, config, threading |
| `folia-server/minecraft-patches/sources/` | โค้ดใหม่ทั้งหมดของ `dev.folia.sea.*` |
| `folia-server/paper-patches/features/` | แพตช์ระดับ Paper |

โค้ดทั้งหมดของ AethraSea อยู่ในแพตช์ ซึ่งถูกคัดลอกเข้า source เวลารัน
`applyAllPatches` — โค้ดใหม่ ๆ เขียนเป็นแพตช์เสมอ เพื่อให้ rebase กับ Folia ต้นทางง่าย

---

## 🏷️ เวอร์ชัน & Release

- ทำงานบน **Folia 26.2** รายละเอียดความเปลี่ยนแปลงดูได้ที่ **Releases** ด้านข้าง
- **Release build** คือไฟล์ `AethraSea-26.2-SNAPSHOT.jar` (paperclip) — เปลี่ยนชื่อเป็น `server.jar` ได้เลย
- แต่ละ version ออกเป็น **tag** เช่น `26.2` — ล็อกเวอร์ชันที่ใช้อยู่เพื่อย้อนกลับได้

---

## 💬 ข้อควรรู้ก่อนอัปเกรด

- 🐘 **ไม่ใช่ vanilla drop-in 100%** — อยู่บน Folia ควรเทสต์ปลั๊กอินที่ใช้จริงก่อน
- 🔌 ปลั๊กอินที่เขียนด้วย **Folia API** จะทำงานแบบ best-in-class (region thread จริง)
- 🔁 ถ้าเจอปัญหา ลองแก้ `sea.yml` แล้ว `/seagui reload` ก่อน restart
- 📝 เจอบั๊ก หรืออยากได้ฟีเจอร์ → เปิด **GitHub Issues** ได้เลย

---

## ⚖️ License

AethraSea เป็น fork จาก **Paper / Folia** — ส่วนที่ได้จากต้นทางอยู่ภายใต้สัญญาอนุญาตของ
Paper/Folia ตามที่ระบุในต้นทาง โปรดตรวจสอบ **LICENSE** ของต้นทางก่อนนำไปเผยแพร่
หรือใช้เชิงพาณิชย์

ส่วนโค้ดที่เขียนขึ้นใหม่โดย AethraSea ตั้งใจให้ทุกคนใช้และต่อยอดได้ หากมีบั๊กก็มาแจ้งกันได้เลย

---

<div align="center">

Made with ❤️

</div>