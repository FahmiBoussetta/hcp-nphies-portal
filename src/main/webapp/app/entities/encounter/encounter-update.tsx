import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { getEntities as getHospitalizations } from 'app/entities/hospitalization/hospitalization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './encounter.reducer';
import { IEncounter } from 'app/shared/model/encounter.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EncounterUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const hospitalizations = useAppSelector(state => state.hospitalization.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const encounterEntity = useAppSelector(state => state.encounter.entity);
  const loading = useAppSelector(state => state.encounter.loading);
  const updating = useAppSelector(state => state.encounter.updating);
  const updateSuccess = useAppSelector(state => state.encounter.updateSuccess);

  const handleClose = () => {
    props.history.push('/encounter');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getHospitalizations({}));
    dispatch(getOrganizations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.start = convertDateTimeToServer(values.start);
    values.end = convertDateTimeToServer(values.end);

    const entity = {
      ...encounterEntity,
      ...values,
      subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
      hospitalization: hospitalizations.find(it => it.id.toString() === values.hospitalizationId.toString()),
      serviceProvider: organizations.find(it => it.id.toString() === values.serviceProviderId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          start: displayDefaultDateTime(),
          end: displayDefaultDateTime(),
        }
      : {
          ...encounterEntity,
          encounterClass: 'AMB',
          start: convertDateTimeFromServer(encounterEntity.start),
          end: convertDateTimeFromServer(encounterEntity.end),
          serviceType: 'N237',
          priority: 'EM',
          subjectId: encounterEntity?.subject?.id,
          hospitalizationId: encounterEntity?.hospitalization?.id,
          serviceProviderId: encounterEntity?.serviceProvider?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.encounter.home.createOrEditLabel" data-cy="EncounterCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.encounter.home.createOrEditLabel">Create or edit a Encounter</Translate>
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
                  id="encounter-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.guid')}
                id="encounter-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.forceId')}
                id="encounter-forceId"
                name="forceId"
                data-cy="forceId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.identifier')}
                id="encounter-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.encounterClass')}
                id="encounter-encounterClass"
                name="encounterClass"
                data-cy="encounterClass"
                type="select"
              >
                <option value="AMB">{translate('hcpNphiesPortalApp.EncounterClassEnum.AMB')}</option>
                <option value="EMER">{translate('hcpNphiesPortalApp.EncounterClassEnum.EMER')}</option>
                <option value="HH">{translate('hcpNphiesPortalApp.EncounterClassEnum.HH')}</option>
                <option value="IMP">{translate('hcpNphiesPortalApp.EncounterClassEnum.IMP')}</option>
                <option value="SS">{translate('hcpNphiesPortalApp.EncounterClassEnum.SS')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.start')}
                id="encounter-start"
                name="start"
                data-cy="start"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.end')}
                id="encounter-end"
                name="end"
                data-cy="end"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.serviceType')}
                id="encounter-serviceType"
                name="serviceType"
                data-cy="serviceType"
                type="select"
              >
                <option value="N237">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N237')}</option>
                <option value="N576">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N576')}</option>
                <option value="N356">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N356')}</option>
                <option value="N621">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N621')}</option>
                <option value="N179">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N179')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.encounter.priority')}
                id="encounter-priority"
                name="priority"
                data-cy="priority"
                type="select"
              >
                <option value="EM">{translate('hcpNphiesPortalApp.ActPriorityEnum.EM')}</option>
                <option value="EL">{translate('hcpNphiesPortalApp.ActPriorityEnum.EL')}</option>
              </ValidatedField>
              <ValidatedField
                id="encounter-subject"
                name="subjectId"
                data-cy="subject"
                label={translate('hcpNphiesPortalApp.encounter.subject')}
                type="select"
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="encounter-hospitalization"
                name="hospitalizationId"
                data-cy="hospitalization"
                label={translate('hcpNphiesPortalApp.encounter.hospitalization')}
                type="select"
              >
                <option value="" key="0" />
                {hospitalizations
                  ? hospitalizations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="encounter-serviceProvider"
                name="serviceProviderId"
                data-cy="serviceProvider"
                label={translate('hcpNphiesPortalApp.encounter.serviceProvider')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/encounter" replace color="info">
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

export default EncounterUpdate;
