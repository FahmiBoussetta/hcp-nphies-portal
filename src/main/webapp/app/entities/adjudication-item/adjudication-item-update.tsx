import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-item.reducer';
import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjudicationItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationItemUpdate = (props: IAdjudicationItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { adjudicationItemEntity, claimResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adjudication-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaimResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjudicationItemEntity,
        ...values,
        claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
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
          <h2 id="hcpNphiesPortalApp.adjudicationItem.home.createOrEditLabel" data-cy="AdjudicationItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.home.createOrEditLabel">Create or edit a AdjudicationItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adjudicationItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjudication-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjudication-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="outcomeLabel" for="adjudication-item-outcome">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.outcome">Outcome</Translate>
                </Label>
                <AvField id="adjudication-item-outcome" data-cy="outcome" type="text" name="outcome" />
              </AvGroup>
              <AvGroup>
                <Label id="sequenceLabel" for="adjudication-item-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="adjudication-item-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="adjudication-item-claimResponse">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.claimResponse">Claim Response</Translate>
                </Label>
                <AvInput
                  id="adjudication-item-claimResponse"
                  data-cy="claimResponse"
                  type="select"
                  className="form-control"
                  name="claimResponseId"
                >
                  <option value="" key="0" />
                  {claimResponses
                    ? claimResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjudication-item" replace color="info">
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
  claimResponses: storeState.claimResponse.entities,
  adjudicationItemEntity: storeState.adjudicationItem.entity,
  loading: storeState.adjudicationItem.loading,
  updating: storeState.adjudicationItem.updating,
  updateSuccess: storeState.adjudicationItem.updateSuccess,
});

const mapDispatchToProps = {
  getClaimResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationItemUpdate);
