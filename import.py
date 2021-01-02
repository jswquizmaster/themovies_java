import sqlite3
import requests
import time

url = "http://localhost:8080/movies"

conn = sqlite3.connect("import/insomnia.sqlite")

cur = conn.cursor()

cur.execute("SELECT * FROM local_movies")

rows = cur.fetchall()

for row in rows:
  subtitleFilename = None

  flist = row[3].split('/')
  filename = '/'.join(flist[-2:])
  #print(filename)

  if (row[4] != None):
    slist = row[4].split('/')
    subtitleFilename = '/'.join(slist[-2:])
    #print(subtitleFilename)

  movie = {
    "createdAt": row[1],
    "updatedAt": row[2],
    "filename": filename,
    "subtitleFilename": subtitleFilename,
    "tmdbId": row[5],
    "format": row[6],
    "bitrate": row[7],
    "duration": row[8],
    "videoCodec": row[9],
    "videoWidth": row[10],
    "videoHeight": row[11],
    "audioCodec": row[12],
    "watched": row[13]
  }
  
  x = requests.post(url, json = movie)
  print(x.text)
  time.sleep(1.0)




