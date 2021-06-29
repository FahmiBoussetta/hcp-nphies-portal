import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './hospitalization.reducer';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HospitalizationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const organizations = useAppSelector(state => state.organization.entities);
  const hospitalizationEntity = useAppSelector(state => state.hospitalization.entity);
  const loading = useAppSelector(state => state.hospitalization.loading);
  const updating = useAppSelector(state => state.hospitalization.updating);
  const updateSuccess = useAppSelector(state => state.hospitalization.updateSuccess);

  const handleClose = () => {
    props.history.push('/hospitalization');
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
      ...hospitalizationEntity,
      ...values,
      origin: organizations.find(it => it.id.toString() === values.originId.toString()),
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
          ...hospitalizationEntity,
          admitSource: 'IA',
          reAdmission: 'R',
          dischargeDisposition: 'Home',
          originId: hospitalizationEntity?.origin?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.hospitalization.home.createOrEditLabel" data-cy="HospitalizationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.createOrEditLabel">Create or edit a Hospitalization</Translate>
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
                  id="hospitalization-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.hospitalization.admitSource')}
                id="hospitalization-admitSource"
                name="admitSource"
                data-cy="admitSource"
                type="select"
              >
                <option value="IA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.IA')}</option>
                <option value="EER">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EER')}</option>
                <option value="EOP">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EOP')}</option>
                <option value="EGPHC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EGPHC')}</option>
                <option value="EGGH">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EGGH')}</option>
                <option value="EPPHC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EPPHC')}</option>
                <option value="EPH">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EPH')}</option>
                <option value="EIC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EIC')}</option>
                <option value="EWGS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWGS')}</option>
                <option value="EWSS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWSS')}</option>
                <option value="EWIS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWIS')}</option>
                <option value="EMBA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EMBA')}</option>
                <option value="PMBA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.PMBA')}</option>
                <option value="Others">{translate('hcpNphiesPortalApp.AdmitSourceEnum.Others')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.hospitalization.reAdmission')}
                id="hospitalization-reAdmission"
                name="reAdmission"
                data-cy="reAdmission"
                type="select"
              >
                <option value="R">{translate('hcpNphiesPortalApp.ReAdmissionEnum.R')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.hospitalization.dischargeDisposition')}
                id="hospitalization-dischargeDisposition"
                name="dischargeDisposition"
                data-cy="dischargeDisposition"
                type="select"
              >
                <option value="Home">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Home')}</option>
                <option value="DASHalt_home">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHalt_home')}</option>
                <option value="DASHother_hcf">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHother_hcf')}</option>
                <option value="Hosp">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Hosp')}</option>
                <option value="DASHlong">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHlong')}</option>
                <option value="Aadvice">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Aadvice')}</option>
                <option value="Exp">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Exp')}</option>
                <option value="Psy">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Psy')}</option>
                <option value="Rehab">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Rehab')}</option>
                <option value="Snf">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Snf')}</option>
                <option value="Oth">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Oth')}</option>
              </ValidatedField>
              <ValidatedField
                id="hospitalization-origin"
                name="originId"
                data-cy="origin"
                label={translate('hcpNphiesPortalApp.hospitalization.origin')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hospitalization" replace color="info">
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

export default HospitalizationUpdate;
