/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package androidrubick.xframework.impl.image.naming;

import androidrubick.encode.MD5;

/**
 * Names image file as MD5 hash of image URI
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.4.0
 */
public class Md5FileNameGenerator implements FileNameGenerator {

	private static final String HASH_ALGORITHM = "MD5";
	private static final int RADIX = 10 + 26; // 10 digits + 26 letters

	@Override
	public String generate(String imageUri) {
        return MD5.makeMd5Sum(imageUri.getBytes());
	}
}
