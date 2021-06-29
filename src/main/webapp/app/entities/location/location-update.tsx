import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LocationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const organizations = useAppSelector(state => state.organization.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    props.history.push('/location');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrganizations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...locationEntity,
      ...values,
      managingOrganization: organizations.find(it => it.id.toString() === values.managingOrganizationId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...locationEntity,
          type: 'DedicatedServiceDeliveryLocationRoleType',
          managingOrganizationId: locationEntity?.managingOrganization?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.location.home.createOrEditLabel">Create or edit a Location</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.location.guid')}
                id="location-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.location.identifier')}
                id="location-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.location.type')}
                id="location-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="DedicatedServiceDeliveryLocationRoleType">
                  {translate('hcpNphiesPortalApp.LocationTypeEnum.DedicatedServiceDeliveryLocationRoleType')}
                </option>
                <option value="DedicatedClinicalLocationRoleType">
                  {translate('hcpNphiesPortalApp.LocationTypeEnum.DedicatedClinicalLocationRoleType')}
                </option>
                <option value="DX">{translate('hcpNphiesPortalApp.LocationTypeEnum.DX')}</option>
                <option value="CVDX">{translate('hcpNphiesPortalApp.LocationTypeEnum.CVDX')}</option>
                <option value="CATH">{translate('hcpNphiesPortalApp.LocationTypeEnum.CATH')}</option>
                <option value="ECHO">{translate('hcpNphiesPortalApp.LocationTypeEnum.ECHO')}</option>
                <option value="GIDX">{translate('hcpNphiesPortalApp.LocationTypeEnum.GIDX')}</option>
                <option value="ENDOS">{translate('hcpNphiesPortalApp.LocationTypeEnum.ENDOS')}</option>
                <option value="RADDX">{translate('hcpNphiesPortalApp.LocationTypeEnum.RADDX')}</option>
                <option value="RADO">{translate('hcpNphiesPortalApp.LocationTypeEnum.RADO')}</option>
                <option value="RNEU">{translate('hcpNphiesPortalApp.LocationTypeEnum.RNEU')}</option>
                <option value="HOSP">{translate('hcpNphiesPortalApp.LocationTypeEnum.HOSP')}</option>
                <option value="CHR">{translate('hcpNphiesPortalApp.LocationTypeEnum.CHR')}</option>
                <option value="GACH">{translate('hcpNphiesPortalApp.LocationTypeEnum.GACH')}</option>
                <option value="MHSP">{translate('hcpNphiesPortalApp.LocationTypeEnum.MHSP')}</option>
                <option value="PSYCHF">{translate('hcpNphiesPortalApp.LocationTypeEnum.PSYCHF')}</option>
                <option value="RH">{translate('hcpNphiesPortalApp.LocationTypeEnum.RH')}</option>
                <option value="RHAT">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHAT')}</option>
                <option value="RHII">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHII')}</option>
                <option value="RHMAD">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHMAD')}</option>
                <option value="RHPI">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHPI')}</option>
                <option value="RHPIH">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHPIH')}</option>
                <option value="RHPIMS">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHPIMS')}</option>
                <option value="RHPIVS">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHPIVS')}</option>
                <option value="RHYAD">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHYAD')}</option>
                <option value="HU">{translate('hcpNphiesPortalApp.LocationTypeEnum.HU')}</option>
                <option value="BMTU">{translate('hcpNphiesPortalApp.LocationTypeEnum.BMTU')}</option>
                <option value="CCU">{translate('hcpNphiesPortalApp.LocationTypeEnum.CCU')}</option>
                <option value="CHEST">{translate('hcpNphiesPortalApp.LocationTypeEnum.CHEST')}</option>
                <option value="EPIL">{translate('hcpNphiesPortalApp.LocationTypeEnum.EPIL')}</option>
                <option value="ER">{translate('hcpNphiesPortalApp.LocationTypeEnum.ER')}</option>
                <option value="ETU">{translate('hcpNphiesPortalApp.LocationTypeEnum.ETU')}</option>
                <option value="HD">{translate('hcpNphiesPortalApp.LocationTypeEnum.HD')}</option>
                <option value="HLAB">{translate('hcpNphiesPortalApp.LocationTypeEnum.HLAB')}</option>
                <option value="INLAB">{translate('hcpNphiesPortalApp.LocationTypeEnum.INLAB')}</option>
                <option value="OUTLAB">{translate('hcpNphiesPortalApp.LocationTypeEnum.OUTLAB')}</option>
                <option value="HRAD">{translate('hcpNphiesPortalApp.LocationTypeEnum.HRAD')}</option>
                <option value="HUSCS">{translate('hcpNphiesPortalApp.LocationTypeEnum.HUSCS')}</option>
                <option value="ICU">{translate('hcpNphiesPortalApp.LocationTypeEnum.ICU')}</option>
                <option value="PEDICU">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDICU')}</option>
                <option value="PEDNICU">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDNICU')}</option>
                <option value="INPHARM">{translate('hcpNphiesPortalApp.LocationTypeEnum.INPHARM')}</option>
                <option value="MBL">{translate('hcpNphiesPortalApp.LocationTypeEnum.MBL')}</option>
                <option value="NCCS">{translate('hcpNphiesPortalApp.LocationTypeEnum.NCCS')}</option>
                <option value="NS">{translate('hcpNphiesPortalApp.LocationTypeEnum.NS')}</option>
                <option value="OUTPHARM">{translate('hcpNphiesPortalApp.LocationTypeEnum.OUTPHARM')}</option>
                <option value="PEDU">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDU')}</option>
                <option value="PHU">{translate('hcpNphiesPortalApp.LocationTypeEnum.PHU')}</option>
                <option value="RHU">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHU')}</option>
                <option value="SLEEP">{translate('hcpNphiesPortalApp.LocationTypeEnum.SLEEP')}</option>
                <option value="NCCF">{translate('hcpNphiesPortalApp.LocationTypeEnum.NCCF')}</option>
                <option value="SNF">{translate('hcpNphiesPortalApp.LocationTypeEnum.SNF')}</option>
                <option value="OF">{translate('hcpNphiesPortalApp.LocationTypeEnum.OF')}</option>
                <option value="ALL">{translate('hcpNphiesPortalApp.LocationTypeEnum.ALL')}</option>
                <option value="AMPUT">{translate('hcpNphiesPortalApp.LocationTypeEnum.AMPUT')}</option>
                <option value="BMTC">{translate('hcpNphiesPortalApp.LocationTypeEnum.BMTC')}</option>
                <option value="BREAST">{translate('hcpNphiesPortalApp.LocationTypeEnum.BREAST')}</option>
                <option value="CANC">{translate('hcpNphiesPortalApp.LocationTypeEnum.CANC')}</option>
                <option value="CAPC">{translate('hcpNphiesPortalApp.LocationTypeEnum.CAPC')}</option>
                <option value="CARD">{translate('hcpNphiesPortalApp.LocationTypeEnum.CARD')}</option>
                <option value="PEDCARD">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDCARD')}</option>
                <option value="COAG">{translate('hcpNphiesPortalApp.LocationTypeEnum.COAG')}</option>
                <option value="CRS">{translate('hcpNphiesPortalApp.LocationTypeEnum.CRS')}</option>
                <option value="DERM">{translate('hcpNphiesPortalApp.LocationTypeEnum.DERM')}</option>
                <option value="ENDO">{translate('hcpNphiesPortalApp.LocationTypeEnum.ENDO')}</option>
                <option value="PEDE">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDE')}</option>
                <option value="ENT">{translate('hcpNphiesPortalApp.LocationTypeEnum.ENT')}</option>
                <option value="FMC">{translate('hcpNphiesPortalApp.LocationTypeEnum.FMC')}</option>
                <option value="GI">{translate('hcpNphiesPortalApp.LocationTypeEnum.GI')}</option>
                <option value="PEDGI">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDGI')}</option>
                <option value="GIM">{translate('hcpNphiesPortalApp.LocationTypeEnum.GIM')}</option>
                <option value="GYN">{translate('hcpNphiesPortalApp.LocationTypeEnum.GYN')}</option>
                <option value="HEM">{translate('hcpNphiesPortalApp.LocationTypeEnum.HEM')}</option>
                <option value="PEDHEM">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDHEM')}</option>
                <option value="HTN">{translate('hcpNphiesPortalApp.LocationTypeEnum.HTN')}</option>
                <option value="IEC">{translate('hcpNphiesPortalApp.LocationTypeEnum.IEC')}</option>
                <option value="INFD">{translate('hcpNphiesPortalApp.LocationTypeEnum.INFD')}</option>
                <option value="PEDID">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDID')}</option>
                <option value="INV">{translate('hcpNphiesPortalApp.LocationTypeEnum.INV')}</option>
                <option value="LYMPH">{translate('hcpNphiesPortalApp.LocationTypeEnum.LYMPH')}</option>
                <option value="MGEN">{translate('hcpNphiesPortalApp.LocationTypeEnum.MGEN')}</option>
                <option value="NEPH">{translate('hcpNphiesPortalApp.LocationTypeEnum.NEPH')}</option>
                <option value="PEDNEPH">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDNEPH')}</option>
                <option value="NEUR">{translate('hcpNphiesPortalApp.LocationTypeEnum.NEUR')}</option>
                <option value="OB">{translate('hcpNphiesPortalApp.LocationTypeEnum.OB')}</option>
                <option value="OMS">{translate('hcpNphiesPortalApp.LocationTypeEnum.OMS')}</option>
                <option value="ONCL">{translate('hcpNphiesPortalApp.LocationTypeEnum.ONCL')}</option>
                <option value="PEDHO">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDHO')}</option>
                <option value="OPH">{translate('hcpNphiesPortalApp.LocationTypeEnum.OPH')}</option>
                <option value="OPTC">{translate('hcpNphiesPortalApp.LocationTypeEnum.OPTC')}</option>
                <option value="ORTHO">{translate('hcpNphiesPortalApp.LocationTypeEnum.ORTHO')}</option>
                <option value="HAND">{translate('hcpNphiesPortalApp.LocationTypeEnum.HAND')}</option>
                <option value="PAINCL">{translate('hcpNphiesPortalApp.LocationTypeEnum.PAINCL')}</option>
                <option value="PC">{translate('hcpNphiesPortalApp.LocationTypeEnum.PC')}</option>
                <option value="PEDC">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDC')}</option>
                <option value="PEDRHEUM">{translate('hcpNphiesPortalApp.LocationTypeEnum.PEDRHEUM')}</option>
                <option value="POD">{translate('hcpNphiesPortalApp.LocationTypeEnum.POD')}</option>
                <option value="PREV">{translate('hcpNphiesPortalApp.LocationTypeEnum.PREV')}</option>
                <option value="PROCTO">{translate('hcpNphiesPortalApp.LocationTypeEnum.PROCTO')}</option>
                <option value="PROFF">{translate('hcpNphiesPortalApp.LocationTypeEnum.PROFF')}</option>
                <option value="PROS">{translate('hcpNphiesPortalApp.LocationTypeEnum.PROS')}</option>
                <option value="PSI">{translate('hcpNphiesPortalApp.LocationTypeEnum.PSI')}</option>
                <option value="PSY">{translate('hcpNphiesPortalApp.LocationTypeEnum.PSY')}</option>
                <option value="RHEUM">{translate('hcpNphiesPortalApp.LocationTypeEnum.RHEUM')}</option>
                <option value="SPMED">{translate('hcpNphiesPortalApp.LocationTypeEnum.SPMED')}</option>
                <option value="SU">{translate('hcpNphiesPortalApp.LocationTypeEnum.SU')}</option>
                <option value="PLS">{translate('hcpNphiesPortalApp.LocationTypeEnum.PLS')}</option>
                <option value="URO">{translate('hcpNphiesPortalApp.LocationTypeEnum.URO')}</option>
                <option value="TR">{translate('hcpNphiesPortalApp.LocationTypeEnum.TR')}</option>
                <option value="TRAVEL">{translate('hcpNphiesPortalApp.LocationTypeEnum.TRAVEL')}</option>
                <option value="WND">{translate('hcpNphiesPortalApp.LocationTypeEnum.WND')}</option>
                <option value="RTF">{translate('hcpNphiesPortalApp.LocationTypeEnum.RTF')}</option>
                <option value="PRC">{translate('hcpNphiesPortalApp.LocationTypeEnum.PRC')}</option>
                <option value="SURF">{translate('hcpNphiesPortalApp.LocationTypeEnum.SURF')}</option>
                <option value="DedicatedNonClinicalLocationRoleType">
                  {translate('hcpNphiesPortalApp.LocationTypeEnum.DedicatedNonClinicalLocationRoleType')}
                </option>
                <option value="DADDR">{translate('hcpNphiesPortalApp.LocationTypeEnum.DADDR')}</option>
                <option value="MOBL">{translate('hcpNphiesPortalApp.LocationTypeEnum.MOBL')}</option>
                <option value="AMB">{translate('hcpNphiesPortalApp.LocationTypeEnum.AMB')}</option>
                <option value="PHARM">{translate('hcpNphiesPortalApp.LocationTypeEnum.PHARM')}</option>
                <option value="IncidentalServiceDeliveryLocationRoleType">
                  {translate('hcpNphiesPortalApp.LocationTypeEnum.IncidentalServiceDeliveryLocationRoleType')}
                </option>
                <option value="ACC">{translate('hcpNphiesPortalApp.LocationTypeEnum.ACC')}</option>
                <option value="COMM">{translate('hcpNphiesPortalApp.LocationTypeEnum.COMM')}</option>
                <option value="CSC">{translate('hcpNphiesPortalApp.LocationTypeEnum.CSC')}</option>
                <option value="PTRES">{translate('hcpNphiesPortalApp.LocationTypeEnum.PTRES')}</option>
                <option value="SCHOOL">{translate('hcpNphiesPortalApp.LocationTypeEnum.SCHOOL')}</option>
                <option value="UPC">{translate('hcpNphiesPortalApp.LocationTypeEnum.UPC')}</option>
                <option value="WORK">{translate('hcpNphiesPortalApp.LocationTypeEnum.WORK')}</option>
              </ValidatedField>
              <ValidatedField
                id="location-managingOrganization"
                name="managingOrganizationId"
                data-cy="managingOrganization"
                label={translate('hcpNphiesPortalApp.location.managingOrganization')}
                type="select"
              >
                <option value="" key="0" />
                {organizations
                  ? organizations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LocationUpdate;
