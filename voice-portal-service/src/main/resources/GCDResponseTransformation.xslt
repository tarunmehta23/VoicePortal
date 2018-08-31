<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output method="xml" indent="yes"/>
<xsl:template match="/">
	
	<xsl:key name='accountLookupKey' match="object[@name='Service']/prop/prop[@name='type']" use="@value"/>	
	<xsl:key name='refIdLookupKey' match="object[@name='Service']/prop/prop[@name='refId']" use="@value"/>
	<xsl:key name='serviceTypesLookupKey' match="object[@name='Service']" use="prop/prop[@name='serviceType']/@value"/>
	
	<xsl:key name='terminationProfileLookupKey' match="object[@name='Service']/object/object[@name='TerminationProfile']" use="prop/prop[@name='mac']/@value"/>	
	
	<Account>
	
		<xsl:for-each select="AbstractObject/prop/prop">
	        <xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
	    </xsl:for-each>
	    
	    <!-- Moving account related serviceCodes to account level -->
	    <serviceCodes>
	    		<xsl:value-of  select="key('accountLookupKey', 'Account')/../prop[@name='serviceCodes']/@value"/>
    		</serviceCodes>
	    
    		
    		<!--Building Address -->
    		<xsl:for-each select="AbstractObject/object/object[@name='Address']">
			<Address>
				<xsl:for-each select="prop/prop">
					<xsl:if test="@name!='addrLine2'">
						<xsl:apply-templates select = "." />
					</xsl:if>
			    </xsl:for-each>
			</Address>
		</xsl:for-each>
		
		<!--Building Device -->
		<xsl:for-each select="AbstractObject/object/object[@name='Device']">
		  	<Device>
				<xsl:for-each select="prop/prop">
					<xsl:if test="@name!='refId'">
			        		<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
			        </xsl:if>
			        
			        <xsl:if test="@name='refId'">
			        		<!-- Move serviceCodes to Device level with matching refId from Service level -->
			        		<serviceCodes><xsl:value-of  select="key('refIdLookupKey', @value)/../prop[@name='serviceCodes']/@value"/></serviceCodes>
			        		
			        		<!-- Move serviceProvider to Device level with matching refId from Service level -->
			        		<serviceProvider><xsl:value-of  select="key('refIdLookupKey', @value)/../prop[@name='serviceProvider']/@value"/></serviceProvider>
			       		
			       		<!-- Getting device properties for mta, here we have to look for matching refId with mac -->
			        		<xsl:for-each select="key('terminationProfileLookupKey', @value)[1]">
			        			<xsl:for-each select="prop/prop">
								<xsl:if test="@name='prefix' or @name='profile' or @name='maxlines'">
									<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
								</xsl:if>
						    </xsl:for-each>
			        		</xsl:for-each>
			        		
			        </xsl:if>
			        
			    </xsl:for-each>
			</Device>
		</xsl:for-each>

		<!--Building User -->
		<xsl:for-each select="AbstractObject/object/object[@name='User']">
			<User>
				<xsl:for-each select="prop/prop">
					<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
			    </xsl:for-each>
			</User>
		</xsl:for-each>		
		
		<!-- Building VMAIL -->
		<xsl:for-each select="key('serviceTypesLookupKey', 'VMAIL')">
			<VMailService>
				<xsl:for-each select="prop/prop">
					<xsl:apply-templates select = "." />
			    </xsl:for-each>
			    
			    <xsl:for-each select="object/object/prop/prop">
			    		<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
			    </xsl:for-each>
			</VMailService>
		</xsl:for-each>
		
		<!-- Building HGROUP -->
		<xsl:for-each select="key('serviceTypesLookupKey', 'HGROUP')">
			<HGroupService>
				<xsl:for-each select="prop/prop">
					<xsl:apply-templates select = "." />
			    </xsl:for-each>
			    
			    <xsl:for-each select="object/object/prop/prop">
			    		<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
			    </xsl:for-each>
			    
			    <Members>
				    <xsl:for-each select="object/object[@name='Terminals']/object/object">
				    		<Member>	
				    			<xsl:for-each select="prop/prop">
								<xsl:apply-templates select = "." />
						    </xsl:for-each>
				    		</Member>
				    </xsl:for-each>
			    </Members>
			</HGroupService>
		</xsl:for-each>
		
		<!-- Building BGROUP -->
  		<xsl:for-each select="key('serviceTypesLookupKey', 'BGROUP')">
			<BGroupService>
				<xsl:for-each select="prop/prop">
					<xsl:apply-templates select = "." />
			    </xsl:for-each>
			    
			    <MainDN>
			    		<xsl:for-each select="object/object[@name='MainDN']/prop/prop">
				    		<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
				    </xsl:for-each>
				    
				    <xsl:for-each select="object/object[@name='MainDN']/object/object/prop/prop">
				    		<xsl:if test="@name='ext'">
				    			<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
				    		</xsl:if>
				    </xsl:for-each>
			    </MainDN>
			    
			    <Members>
				    <xsl:for-each select="object/object[@name='Members']/object/object">
				    		<Member>	
				    			<xsl:for-each select="prop/prop">
								<xsl:apply-templates select = "." />
						    </xsl:for-each>
				    		</Member>
				    </xsl:for-each>
			    </Members>
			</BGroupService>
		</xsl:for-each>
		
		<!-- Building DPHONE -->
		<xsl:for-each select="key('serviceTypesLookupKey', 'DPHONE')">
			<DPhoneService>
				<xsl:for-each select="prop/prop">
					<xsl:apply-templates select = "." />
			    </xsl:for-each>
			    <callAgent><xsl:value-of select="object/object[@name='TerminationProfile']/prop/prop[@name='callAgent']/@value"/></callAgent>
			    <xsl:for-each select="object/object[@name='LineProperties']/prop/prop">
			    			<xsl:if test="@name !='publicIdentityUserId' and @name !='PrivateIdentity'">
				    			<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
				    		</xsl:if>
			    	</xsl:for-each>
			    
			    <TerminationProfile>
			    		<xsl:for-each select="object/object[@name='TerminationProfile']/prop/prop">
			    			<xsl:if test="@name='type' or @name='mac' or @name='port'">
				    			<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
				    		</xsl:if>
			    		</xsl:for-each>
			    	</TerminationProfile>
			    	
			    	<Features>
			    		<xsl:for-each select="object/object[@name='Features']/prop/prop">
			    			<xsl:element name="{@name}"><xsl:value-of select="@value"/></xsl:element>
			    		</xsl:for-each>
			    		
			    		<xsl:for-each select="object/object[@name='Features']/object/object">
			    			<xsl:if test="@name='3WAY'">
			    				<xsl:element name="_{@name}"/>
			    			</xsl:if>
			    			<xsl:if test="@name!='3WAY'">
			    				<xsl:element name="{@name}">
					    			<xsl:for-each select="prop/prop">
									<xsl:apply-templates select = "." />
							    </xsl:for-each>
						    </xsl:element>
			    			</xsl:if>
			    		</xsl:for-each>
			    		
			    	</Features>
			    
			</DPhoneService>
		</xsl:for-each>
		
		<!-- Building AGROUP -->
		<xsl:for-each select="key('serviceTypesLookupKey', 'AGROUP')">
			<AGroupService>
				<xsl:for-each select="prop/prop">
					<xsl:apply-templates select = "." />
			    </xsl:for-each>
			    
			    <coverphonenumber><xsl:value-of select="object/object[@name='PROPERTIES']/prop/prop[@name='coverphonenumber']/@value"/></coverphonenumber>

				<Attendants>
					<xsl:for-each select="object/object[@name='ATTENDANTS']/object/object">
				    		<Attendant>	
				    			<xsl:for-each select="prop/prop">
								<xsl:apply-templates select = "." />
						    </xsl:for-each>
				    		</Attendant>
				    </xsl:for-each>
				</Attendants>			    
			    
			    <Members>
				    <xsl:for-each select="object/object[@name='Members']/object/object">
				    		<Member>	
				    			<xsl:for-each select="prop/prop">
								<xsl:apply-templates select = "." />
						    </xsl:for-each>
				    		</Member>
				    </xsl:for-each>
			    </Members>
			</AGroupService>
		</xsl:for-each>	
		
  	</Account>
  	
</xsl:template>


<xsl:template match = "prop/prop"> 
	<xsl:if test="@name!='Service.serviceUid' and @name!='serviceUid'">
	     <xsl:variable name="tempName">
	    		<xsl:choose>
		        <xsl:when test="@name='serviceType'">
		            <xsl:text>type</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='serviceId'">
		        		<xsl:choose>
		        			<xsl:when test="../prop[@name='serviceType']/@value = 'HGROUP'">
				            <xsl:text>pilotPhoneNumber</xsl:text>
				        </xsl:when>
				        <xsl:otherwise>
				        		<xsl:text>phoneNumber</xsl:text>
				        </xsl:otherwise>
		        		</xsl:choose>
		        </xsl:when>
		        <xsl:when test="@name='serviceCategory'">
		            <xsl:text>category</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='serviceKey'">
		            <xsl:text>subscriberId</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='serviceStatus'">
		            <xsl:text>status</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='serviceProvider'">
		            <xsl:text>provider</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='serviceContext'">
		            <xsl:text>context</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='rcDesc'">
		            <xsl:text>rateCenterName</xsl:text>
		        </xsl:when>
		        <xsl:when test="@name='rcState'">
		            <xsl:text>rateCenterState</xsl:text>
		        </xsl:when>
		         <xsl:when test="@name='900'">
		            <xsl:text>_900</xsl:text>
		        </xsl:when>
		         <xsl:when test="@name='976'">
		            <xsl:text>_976</xsl:text>
		        </xsl:when>
		         <xsl:when test="@name='0+'">
		            <xsl:text>_0PLUS</xsl:text>
		        </xsl:when>
		         <xsl:when test="@name='411'">
		            <xsl:text>_411</xsl:text>
		        </xsl:when>
		        <xsl:otherwise>
		        		<xsl:value-of select="@name"/>
		        </xsl:otherwise>
	    		</xsl:choose>
		</xsl:variable>
		<xsl:element name="{$tempName}"><xsl:value-of select="@value"/></xsl:element>
	 </xsl:if>
</xsl:template>

</xsl:stylesheet>


