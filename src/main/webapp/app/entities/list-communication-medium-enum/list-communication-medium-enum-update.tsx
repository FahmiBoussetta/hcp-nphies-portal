import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-communication-medium-enum.reducer';
import { IListCommunicationMediumEnum } from 'app/shared/model/list-communication-medium-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListCommunicationMediumEnumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const communications = useAppSelector(state => state.communication.entities);
  const listCommunicationMediumEnumEntity = useAppSelector(state => state.listCommunicationMediumEnum.entity);
  const loading = useAppSelector(state => state.listCommunicationMediumEnum.loading);
  const updating = useAppSelector(state => state.listCommunicationMediumEnum.updating);
  const updateSuccess = useAppSelector(state => state.listCommunicationMediumEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-communication-medium-enum');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCommunications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...listCommunicationMediumEnumEntity,
      ...values,
      communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
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
          ...listCommunicationMediumEnumEntity,
          cm: 'ELECTRONIC',
          communicationId: listCommunicationMediumEnumEntity?.communication?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.listCommunicationMediumEnum.home.createOrEditLabel"
            data-cy="ListCommunicationMediumEnumCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.home.createOrEditLabel">
              Create or edit a ListCommunicationMediumEnum
            </Translate>
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
                  id="list-communication-medium-enum-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.listCommunicationMediumEnum.cm')}
                id="list-communication-medium-enum-cm"
                name="cm"
                data-cy="cm"
                type="select"
              >
                <option value="ELECTRONIC">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.ELECTRONIC')}</option>
                <option value="PHYSICAL">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.PHYSICAL')}</option>
                <option value="REMOTE">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.REMOTE')}</option>
                <option value="VERBAL">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.VERBAL')}</option>
                <option value="DICTATE">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.DICTATE')}</option>
                <option value="FACE">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.FACE')}</option>
                <option value="PHONE">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.PHONE')}</option>
                <option value="VIDEOCONF">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.VIDEOCONF')}</option>
                <option value="WRITTEN">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.WRITTEN')}</option>
                <option value="FAXWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.FAXWRIT')}</option>
                <option value="HANDWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.HANDWRIT')}</option>
                <option value="MAILWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.MAILWRIT')}</option>
                <option value="ONLINEWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.ONLINEWRIT')}</option>
                <option value="EMAILWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.EMAILWRIT')}</option>
                <option value="TYPEWRIT">{translate('hcpNphiesPortalApp.CommunicationMediumEnum.TYPEWRIT')}</option>
              </ValidatedField>
              <ValidatedField
                id="list-communication-medium-enum-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.listCommunicationMediumEnum.communication')}
                type="select"
              >
                <option value="" key="0" />
                {communications
                  ? communications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/list-communication-medium-enum"
                replace
                color="info"
              >
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

export default ListCommunicationMediumEnumUpdate;
