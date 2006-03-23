/* 
 * Copyright (c) 2002-2004 LWJGL Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lwjgl.test.fmod3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.lwjgl.fmod3.FMOD;
import org.lwjgl.fmod3.FMODException;
import org.lwjgl.fmod3.FSound;
import org.lwjgl.fmod3.FSoundStream;

/**
 * <br>
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 * $Id$
 */
public class CDDAPlayer {

	public static void main(String[] args) {
		try {
			FMOD.create();
		} catch (FMODException fmode) {
			fmode.printStackTrace();
			System.exit(0);
		}

		System.out.println("Initializing FMOD");
		if (!FSound.FSOUND_Init(44100, 32, 0)) {
			System.out.println("Failed to initialize FMOD");
			System.exit(0);

		}

		boolean running = true;
		String token = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    FSoundStream stream = null;
		do {
      System.out.println(FMOD.FMOD_ErrorString(FSound.FSOUND_GetError()));
			System.out.println("FMOD CD Player test. Press a key corresponding to action");
			System.out.println("1: FSOUND_Stream_Open \"<drive>:\"");
			System.out.println("2: FSOUND_Stream_Open \"<drive>:?\"");
			System.out.println("3: FSOUND_Stream_Open \"<drive>:!\"");
			System.out.println("4: FSOUND_Stream_SetSubStream <position>");
			System.out.println("5: FSOUND_Stream_GetNumSubStreams");
			System.out.println("6: Enumerate tag fields");
			System.out.println("0: Exit");
			try {
				StringTokenizer st = new StringTokenizer(br.readLine().trim());
				token = st.nextToken();

				switch (Integer.parseInt(token)) {
					case 0:
						running = false;
						break;
					case 1:
						stream = FSound.FSOUND_Stream_Open(st.nextToken() + ":", FSound.FSOUND_NORMAL, 0, 0);
						break;
					case 2:
            stream = FSound.FSOUND_Stream_Open(st.nextToken() + ":*?", 0, 0, 0);
						break;
					case 3:
            stream = FSound.FSOUND_Stream_Open(st.nextToken() + ":*!", 0, 0, 0);
						break;
					case 4:
            FSound.FSOUND_Stream_SetSubStream(stream, Integer.parseInt(st.nextToken()));
						break;
					case 5:
            System.out.println(FSound.FSOUND_Stream_GetNumSubStreams(stream));
						break;
					case 6:
            //
						break;
					default:
						System.out.println("No entry");
				}
        System.out.println("Stream: " + stream);
			} catch (Exception e) {
			}
		} while (running);

    if(stream != null) {
    	FSound.FSOUND_Stream_Close(stream);
    }
		FSound.FSOUND_Close();
		FMOD.destroy();
		System.exit(0);
	}
	

}