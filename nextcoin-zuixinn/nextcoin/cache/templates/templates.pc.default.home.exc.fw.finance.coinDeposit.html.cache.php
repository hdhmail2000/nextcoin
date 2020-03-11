													<div class="col-xs-12 take-data-tetil">
                                                        <h3><?php echo $finance['deposit_address']; ?></h3>
                                                    </div>
                                                    <div class="col-xs-12 take-data-type">
                                                        <h1 class="text-left take-data-num">
                                                        		<?php echo $data['rechargeAddress']['fadderess']; ?>
                                                        	</h1>
                                                        <input type="hidden" id="recharge-addresss-<?php echo $data['coinType']['id']; ?>" value="<?php echo $data['rechargeAddress']['fadderess']; ?>" />
                                                        <div class="text-right take-data-nav">
                                                            <button id="copy-btn-<?php echo $data['coinType']['id']; ?>"><?php echo $finance['recommend_copy']; ?></button>
                                                            <button><?php echo $finance['recommend_qrcode']; ?></button>
                                                            <div class="take-data-img" id="qrcode-<?php echo $data['coinType']['id']; ?>">
                                                                
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 take-data-text">
                                                        <p><?php echo $finance['deposit_notice']; ?></p>
                                                        <p>1、<?php echo $finance['deposit_notice2'];  echo $data['coinType']['shortname'];  echo $finance['deposit_notice2_1'];  echo $data['coinType']['shortname'];  echo $finance['deposit_notice2_2'];  echo $data['coinType']['shortname'];  echo $finance['deposit_notice2_3']; ?></p>
                                                        <p>2、<?php echo $finance['deposit_notice3']; ?>0.001<?php echo $data['coinType']['shortname']; ?> </p>
                                                    </div>
                                                    
                                                    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/jquery.qrcode.min.js" type="text/javascript" charset="utf-8"></script>
													<script type="text/javascript">
														var coinId = <?php echo $data['coinType']['id']; ?>;
														var rechargeAddresss = '#recharge-addresss-'+coinId;
														var qrcodeId = '#qrcode-'+coinId;
														var qrtext = $(rechargeAddresss).val();
														if(qrtext != '') {
															if (navigator.userAgent.indexOf("MSIE") > 0) {
														        jQuery(qrcodeId).qrcode({
														            text : qrtext,
														            width : "92",
														            height : "92",
														            render : "table"
														        });
														    } else {
														        jQuery(qrcodeId).qrcode({
														            text :qrtext ,
														            width : "92",
														            height : "92"
														        });
														    }
														
														}else{
															$('.take-data-img').remove();
														}
														    
														$("#copy-btn-"+coinId).click(function(){
															CopyToClipboard(qrtext);
														});
														
														function CopyToClipboard (input) {
															var textToClipboard = input;
															
															var success = true;
															if (window.clipboardData) { // Internet Explorer
															    window.clipboardData.setData ("Text", textToClipboard);
															}
															else {
															        // create a temporary element for the execCommand method
															    var forExecElement = CreateElementForExecCommand (textToClipboard);
															
															            /* Select the contents of the element 
															                (the execCommand for 'copy' method works on the selection) */
															    SelectContent (forExecElement);
															
															    var supported = true;
															
															        // UniversalXPConnect privilege is required for clipboard access in Firefox
															    try {
															        if (window.netscape && netscape.security) {
															            netscape.security.PrivilegeManager.enablePrivilege ("UniversalXPConnect");
															        }
															
															            // Copy the selected content to the clipboard
															            // Works in Firefox and in Safari before version 5
															        success = document.execCommand ("copy", false, null);
															    }
															    catch (e) {
															        success = false;
															    }
															
															        // remove the temporary element
															    document.body.removeChild (forExecElement);
															}
															
															if (success) {
//															    alert ("The text is on the clipboard, try to paste it!");
																alert("复制成功!");
															}
															else {
//															    alert ("Your browser doesn't allow clipboard access!");
																alert("你的浏览器不支持此功能");
															}
														}
											
														function CreateElementForExecCommand (textToClipboard) {
															var forExecElement = document.createElement ("div");
															    // place outside the visible area
															forExecElement.style.position = "absolute";
															forExecElement.style.left = "-10000px";
															forExecElement.style.top = "-10000px";
															    // write the necessary text into the element and append to the document
															forExecElement.textContent = textToClipboard;
															document.body.appendChild (forExecElement);
															    // the contentEditable mode is necessary for the  execCommand method in Firefox
															forExecElement.contentEditable = true;
															
															return forExecElement;
														}
											
														function SelectContent (element) {
															    // first create a range
															var rangeToSelect = document.createRange ();
															rangeToSelect.selectNodeContents (element);
															
															    // select the contents
															var selection = window.getSelection ();
															selection.removeAllRanges ();
															selection.addRange (rangeToSelect);
														}
														    
														    
													</script>
