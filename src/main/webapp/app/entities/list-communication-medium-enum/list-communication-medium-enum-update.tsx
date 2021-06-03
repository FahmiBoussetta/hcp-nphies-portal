import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-communication-medium-enum.reducer';
import { IListCommunicationMediumEnum } from 'app/shared/model/list-communication-medium-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IListCommunicationMediumEnumUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListCommunicationMediumEnumUpdate = (props: IListCommunicationMediumEnumUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { listCommunicationMediumEnumEntity, communications, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/list-communication-medium-enum');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCommunications();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...listCommunicationMediumEnumEntity,
        ...values,
        communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : listCommunicationMediumEnumEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="list-communication-medium-enum-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="list-communication-medium-enum-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="cmLabel" for="list-communication-medium-enum-cm">
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.cm">Cm</Translate>
                </Label>
                <AvInput
                  id="list-communication-medium-enum-cm"
                  data-cy="cm"
                  type="select"
                  className="form-control"
                  name="cm"
                  value={(!isNew && listCommunicationMediumEnumEntity.cm) || 'ELECTRONIC'}
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
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="list-communication-medium-enum-communication">
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.communication">Communication</Translate>
                </Label>
                <AvInput
                  id="list-communication-medium-enum-communication"
                  data-cy="communication"
                  type="select"
                  className="form-control"
                  name="communicationId"
                >
                  <option value="" key="0" />
                  {communications
                    ? communications.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/list-communication-medium-enum" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  communications: storeState.communication.entities,
  listCommunicationMediumEnumEntity: storeState.listCommunicationMediumEnum.entity,
  loading: storeState.listCommunicationMediumEnum.loading,
  updating: storeState.listCommunicationMediumEnum.updating,
  updateSuccess: storeState.listCommunicationMediumEnum.updateSuccess,
});

const mapDispatchToProps = {
  getCommunications,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListCommunicationMediumEnumUpdate);
