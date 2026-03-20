import telebot
import requests
import time
import datetime
import json

BOT_TOKEN = "8355665621:AAFMliZhA9iDn0HxNjpHzApckzBJJTadlPU"
CHAT_ID = "7544264969"
MY_HOUSE = "3"

URL = "https://www.dtek-krem.com.ua/ua/ajax"

COOKIES = "_language=3eb69d58ec89e92ef3dafa6c5ddfe948ae4ccc42f3f8fc9cd1b9568ed22f10a3a%3A2%3A%7Bi%3A0%3Bs%3A9%3A%22_language%22%3Bi%3A1%3Bs%3A2%3A%22uk%22%3B%7D; _csrf-dtek-krem=a1018d668550dadd340869ff140bc150efd1807c1747945252a818b34524e3f6a%3A2%3A%7Bi%3A0%3Bs%3A15%3A%22_csrf-dtek-krem%22%3Bi%3A1%3Bs%3A32%3A%22ge8GxZwri7bmWGF9tMf6WLroxFLRtRQz%22%3B%7D; visid_incap_2398465=uNiVPch+QdCOfG8dz2LJctwOdmkAAAAAQUIPAAAAAADKYRD0dVgO9g/l3oBOs0aj; incap_ses_519_2398465=H9QAB/tMkWbiqqmGLdwzB3LUfGkAAAAA5knbrlf5T3sKyORfx6IzAA==; incap_ses_325_2398465=vc60a6lkjVqMx6dDTqKCBHmfgGkAAAAAt1MejZ/jPZHh3fRXXT2/Fw==; _gid=GA1.3.1835275926.1770037085; dtek-krem=94o977t5vfs6qk1tlntb7n1mct; _ga_4XQ0JLPPD8=GS2.1.s1770041812$o6$g1$t1770042669$j60$l0$h0; _ga=GA1.3.1184789415.1769344728; _gat_gtag_UA_180679529_1=1; incap_wrt_378=VrWAaQAAAACE/rstGgAI+gIQrf+TlNkBGILtgswGIAIoyuqCzAYwA4VRulzobPyQURX+uwrcB5I="

CSRF_TOKEN = "wCM42zDziDMA0sUnitjGnR6IzJnaGBNR0YqOiw-E_7anRgCcSKn_QWnlp0rdn4CkasWqr41UYT6pzMLZe9auzA=="

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
    "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
    "X-Requested-With": "XMLHttpRequest",
    "Origin": "https://www.dtek-krem.com.ua",
    "Referer": "https://www.dtek-krem.com.ua/ua/shutdowns",
    "Cookie": COOKIES,
    "X-CSRF-Token": CSRF_TOKEN
}

FORM_DATA = {
    "method": "getHomeNum",
    "data[0][name]": "city",
    "data[0][value]": "с. Маковище",
    "data[1][name]": "street",
    "data[1][value]": "вул. Садова"
}

bot = telebot.TeleBot(BOT_TOKEN)

def get_schedule_info():
    try:
        resp = requests.post(URL, data=FORM_DATA, headers=HEADERS, timeout=30)
        
        if resp.status_code != 200:
            return None
        
        data = resp.json()
        outage_list = data.get("data", {})
        
        if isinstance(outage_list, list):
            outage_list = {}

        if MY_HOUSE in outage_list:
            info = outage_list[MY_HOUSE]
            return {
                "has_outage": True,
                "text_signature": f"{info.get('start_date')} - {info.get('end_date')}",
                "start": info.get("start_date", "?"),
                "end": info.get("end_date", "?"),
                "reason": info.get("sub_type", "Плановое отключение")
            }
        else:
            return {
                "has_outage": False,
                "text_signature": "CLEAR",
            }

    except Exception:
        return None

def send_status_message(info, is_startup=False):
    try:
        timestamp = datetime.datetime.now().strftime("%H:%M")
        
        if is_startup:
            header = f"🤖 <b>Бот запущен!</b> [{timestamp}]\nТекущая ситуация:\n\n"
        else:
            header = f"⚡️ <b>График изменился!</b> [{timestamp}]\nНовые данные:\n\n"

        if info["has_outage"]:
            msg = (f"{header}"
                   f"🔴 <b>СВЕТА НЕТ</b>\n"
                   f"🕒 Начало: {info['start']}\n"
                   f"🕒 Включение: <b>{info['end']}</b>\n"
                   f"📝 Причина: {info['reason']}")
        else:
            msg = (f"{header}"
                   f"🟢 <b>СВЕТ ЕСТЬ</b>\n"
                   f"Дома нет в списке отключений.")

        bot.send_message(CHAT_ID, msg, parse_mode="HTML")
        
    except Exception:
        pass

def main_loop():
    last_signature = None

    while True:
        current_info = get_schedule_info()

        if current_info is None:
            time.sleep(60)
            continue
        
        current_sig = current_info["text_signature"]

        if last_signature is None:
            send_status_message(current_info, is_startup=True)
            last_signature = current_sig
            
        elif current_sig != last_signature:
            send_status_message(current_info, is_startup=False)
            last_signature = current_sig
            
        time.sleep(1800)

if __name__ == "__main__":
    main_loop()
